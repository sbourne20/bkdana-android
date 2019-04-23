package rzgonz.bkd.modules.daftar.mikro.pribadi

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.util.Log
import kotlinx.android.synthetic.main.activity_daftar_mikro.*
import rzgonz.bkd.Apps.APKModel
import rzgonz.bkd.R
import rzgonz.bkd.injector.User.DaggerUserComponent
import rzgonz.bkd.models.user.UserContent
import rzgonz.core.kotlin.activity.DIBaseActivity
import java.util.*
import javax.inject.Inject
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.header_daftar.*
import rzgonz.bkd.models.provinsi.ProvinsiItem
import rzgonz.bkd.models.provinsi.ProvinsiResponse
import rzgonz.bkd.modules.Login.LoginActivity
import rzgonz.bkd.modules.Login.tokenz
import rzgonz.bkd.modules.daftar.mikro.usaha.DaftarMirkoUsahaActivity
import rzgonz.bkd.modules.profile.adapter.ProvinsiAdapter
import rzgonz.core.kotlin.helper.SharedPreferenceService


class DaftarMikroActivity : DIBaseActivity(),DaftarMikroContract.View {

    @Inject
    lateinit var mPresenter : DaftarMirkoPresenter

    var myData: UserContent? = null

    val myCalendar = Calendar.getInstance()

    val listProvinsi = ArrayList<ProvinsiItem>()

    var provinsi = ""

    override fun initLayout(): Int {
        return R.layout.activity_daftar_mikro
    }

    override fun initUI(savedInstanceState: Bundle?) {
        collapsing_toolbar.isTitleEnabled = false
        collapsing_toolbar.title = "Daftat BKDana Mikro"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Daftar BKDana Mikro")
        btnSelanjutnya.setOnClickListener {
            if(inputOK()) {

                sendMyData()
            }
        }
        showProgressDialog(this,"Mohon Tunggu",true)
        mPresenter.getfcmToken(tokenz)
        mPresenter.getMyData()

//        val adapter = ArrayAdapter<String>(this,
//                android.R.layout.simple_dropdown_item_1line, resources.getStringArray(R.array.array_provinsi))
//
//        spProvinsi.setAdapter<ArrayAdapter<String>>(adapter)
    }

    private fun sendMyData() {
        if(inputOK()){
            myData?.tempatLahir = etTempatLahir.text.toString()
            myData?.jenisKelamin = spGender.selectedItem.toString()
            myData?.tanggalLahir = myCalendar.time.toLocaleString()
            myData?.alamat      = etAlamat.text.toString()
            myData?.kota = etKota.text.toString()
             myData?.provinsi = listProvinsi.get(spProvinsi.selectedItemPosition).provinceName
            myData?.kodepos = etKodePost.text.toString()
            mPresenter.sendMyData(myData!!)
        }
    }

    override fun inject() {
        DaggerUserComponent.builder().appsComponent(APKModel.appsComponent).build().inject(this)

    }
    override fun onAttachView() {
        mPresenter.attachView(this)
    }

    override fun onDetachView() {
        mPresenter.detachView()
    }

    override fun returnUser(status: Boolean, responde: UserContent?, message: String) {
        progressDialog?.dismiss()
        if(status){
            etTempatLahir.setText("${responde?.tempatLahir}")
            if(responde?.jenisKelamin.equals("pria",true)){
                spGender.setSelection(0)
            }else{
                spGender.setSelection(1)
            }
            etAlamat.setText("${responde?.alamat}")
            etKota.setText("${responde?.kota}")
            provinsi = "${responde?.provinsi}"
            etKodePost.setText("${responde?.kodepos}")
            myData = responde
            mPresenter.getProvinsi()
        }
    }

    override fun returnSendUser(status: Boolean, responde: String?, message: String) {
        Log.d("USER","${responde}")
        progressDialog?.dismiss()
        if(status){
            DaftarMirkoUsahaActivity.startThisActivity(this,myData!!)
        }
    }

    override fun returnFcmtoken(status: Boolean, responde: UserContent?, message: String) {
        if(message.equals("fail")){
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Peringatan")
            builder.setMessage("Anda telah masuk ke perangkat baru, mohon untuk ulangi proses " +
                    "login jika ingin menggunakan aplikasi di perangkat ini ")
            builder.setPositiveButton("Masuk Kembali") { dialog, which ->
                val PREFERENCE_NAME = SharedPreferenceService::class.java.getPackage()!!.toString() + ".sharedprefs"
                val settings = getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)
                settings.edit().clear().apply()
                finish()
                startActivity(Intent(baseContext, LoginActivity::class.java))
            }
            builder.setCancelable(false);
            builder.show()
        }
    }

    private fun inputOK(): Boolean {

        if(etTempatLahir.text.isNullOrEmpty()){
            etTempatLahir.setError( "is required!" )
            return  false
        }
        if(etAlamat.text.isNullOrEmpty()){
            etAlamat.setError( "is required!" )
            return  false
        }

        if(etKota.text.isNullOrEmpty()){
            etKota.setError( "is required!" )
            return  false
        }


        if(spProvinsi.selectedItemPosition == 0 ){
            showMessage( "Provinsi Belum Dipilih" )
            return  false
        }


        return true
    }

    override fun returnProvinsi(status: Boolean, responde: ProvinsiResponse?, message: String) {
        if(status){
            var inde = 0
            listProvinsi.add(ProvinsiItem("Pilih Provinsi"))
            responde?.content?.forEachIndexed { index, provinsiItem ->
                listProvinsi.add(provinsiItem!!)
                if(provinsi.equals(provinsiItem.provinceName)){
                    inde= index+1
                }
            }
            val adater = ProvinsiAdapter(baseContext,listProvinsi.toList())
            spProvinsi.adapter = adater
            spProvinsi.setSelection(inde)
        }else{
            showMessage(message)
        }
    }

}
