package rzgonz.bkd.modules.daftar.kilat.pribadi

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_daftar_kilat.*
import kotlinx.android.synthetic.main.header_daftar.*
import rzgonz.bkd.R
import rzgonz.core.kotlin.activity.DIBaseActivity
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.MotionEvent
import android.view.View
import rzgonz.bkd.Apps.APKModel
import rzgonz.bkd.Apps.dismissKeyboard
import rzgonz.bkd.injector.User.DaggerUserComponent
import rzgonz.bkd.models.provinsi.ProvinsiItem
import rzgonz.bkd.models.provinsi.ProvinsiResponse
import rzgonz.bkd.models.user.UserContent
import rzgonz.bkd.modules.Login.LoginActivity
import rzgonz.bkd.modules.Login.tokenz
import rzgonz.bkd.modules.daftar.kilat.datadiri.DaftarKilatDataDiriActivity
import rzgonz.bkd.modules.profile.adapter.ProvinsiAdapter
import rzgonz.core.kotlin.helper.SharedPreferenceService
import java.util.*

import java.text.SimpleDateFormat
import javax.inject.Inject


class DaftarKilatActivity : DIBaseActivity(),DaftarKilatContract.View {

    @Inject
    lateinit var mPresenter : DaftarKilatPresenter

    var myData: UserContent? = null

    val myCalendar = Calendar.getInstance()

    val listProvinsi = ArrayList<ProvinsiItem>()

    var provinsi = ""
    override fun initLayout(): Int {
        return R.layout.activity_daftar_kilat
    }

    override fun initUI(savedInstanceState: Bundle?) {
        collapsing_toolbar.isTitleEnabled = false
        collapsing_toolbar.title = "Daftat BKDana Kilat"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Daftar BKDana Kilat")
        btnSelanjutnya.setOnClickListener {
            sendMyData()
            //startActivity(Intent(this,DaftarUploadKilatActivity::class.java))
        }


        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel()
        }

        etTanggalLahir.setOnTouchListener(object : View.OnTouchListener{
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                if(p1?.action == MotionEvent.ACTION_DOWN){
                    DatePickerDialog(this@DaftarKilatActivity, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show()

                }
                etTanggalLahir.dismissKeyboard()
                return false
            }
        })
        showProgressDialog(this,"Mohon Tunggu",true)
        mPresenter.getfcmToken(tokenz)
        mPresenter.getMyData()
    }

    private fun sendMyData() {
       if(inputOK()){
           showProgressDialog(this,"Upload Progress",true)
           myData?.tempatLahir = etTempatLahir.text.toString()
           myData?.jenisKelamin = spGender.selectedItem.toString()
           myData?.tanggalLahir = myCalendar.time.toLocaleString()
           myData?.alamat      = etAlamat.text.toString()
           myData?.kota = etKota.text.toString()
           myData?.provinsi = listProvinsi.get(spProvinsi.selectedItemPosition).provinceName
           myData?.kodepos = etKodePos.text.toString()
           myData?.nomorNik = etNIK.text.toString()
           mPresenter.sendMyData(myData!!)
       }
    }

    private fun updateLabel() {
        val myFormat = "dd-MM-yyyy" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)

        etTanggalLahir.setText(sdf.format(myCalendar.time))
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

    override fun onResume() {
        super.onResume()
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
            etTanggalLahir.setText("${responde?.tanggalLahir}")
            etAlamat.setText("${responde?.alamat}")
            etKota.setText("${responde?.kota}")
            provinsi = "${responde?.provinsi}"
            etKodePos.setText("${responde?.kodepos}")
            spPekerjaan.setSelection(responde?.pekerjaan!!.toInt().minus(1))
            etNIK.setText("${responde?.nomorNik}")
            myData = responde
            mPresenter.getProvinsi()
            Log.d("user","${status}, ${myData}")
        }
    }

    override fun returnSendUser(status: Boolean, responde: String?, message: String) {
        progressDialog?.dismiss()
        if(status){
            Log.d("user1","${status}, ${myData}")
            DaftarKilatDataDiriActivity.startThisActivity(this,myData!!)
        }else{
            showError(message)
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
        if(etTanggalLahir.text.isNullOrEmpty()){
            etTanggalLahir.setError( "is required!" )
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

        if(etKodePos.text.isNullOrEmpty()){
            etKodePos.setError( "is required!" )
            return  false
        }

        if(etNIK.text.isNullOrEmpty()){
            etNIK.setError( "is required!" )
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
            spProvinsi.adapter= adater
            spProvinsi.setSelection(inde)
        }else{
            showMessage(message)
        }
    }
}
