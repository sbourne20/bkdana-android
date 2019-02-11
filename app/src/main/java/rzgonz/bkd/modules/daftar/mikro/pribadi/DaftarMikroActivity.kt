package rzgonz.bkd.modules.daftar.mikro.pribadi

import android.os.Bundle
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
import rzgonz.bkd.modules.daftar.mikro.usaha.DaftarMirkoUsahaActivity


class DaftarMikroActivity : DIBaseActivity(),DaftarMikroContract.View {

    @Inject
    lateinit var mPresenter : DaftarMirkoPresenter

    var myData: UserContent? = null

    val myCalendar = Calendar.getInstance()

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
                showProgressDialog(this,"Upload Progress",true)
                sendMyData()
            }
        }
        mPresenter.getMyData()

        val adapter = ArrayAdapter<String>(this,
                android.R.layout.simple_dropdown_item_1line, resources.getStringArray(R.array.array_provinsi))

        spProvinsi.setAdapter<ArrayAdapter<String>>(adapter)
    }

    private fun sendMyData() {
        if(inputOK()){
            myData?.tempatLahir = etTempatLahir.text.toString()
            myData?.jenisKelamin = spGender.selectedItem.toString()
            myData?.tanggalLahir = myCalendar.time.toLocaleString()
            myData?.alamat      = etAlamat.text.toString()
            myData?.kota = etKota.text.toString()
            myData?.provinsi = spProvinsi.text.toString()
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
        if(status){
            etTempatLahir.setText("${responde?.tempatLahir}")
            if(responde?.jenisKelamin.equals("pria")){
                spGender.setSelection(0)
            }else{
                spGender.setSelection(1)
            }
            etAlamat.setText("${responde?.alamat}")
            etKota.setText("${responde?.kota}")
            spProvinsi.setText("${responde?.provinsi}")
            etKodePost.setText("${responde?.kodepos}")
            myData = responde
        }
    }

    override fun returnSendUser(status: Boolean, responde: String?, message: String) {
        Log.d("USER","${responde}")
        progressDialog?.dismiss()
        if(status){
            DaftarMirkoUsahaActivity.startThisActivity(this,myData!!)
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


        if(spProvinsi.text.isNullOrEmpty()){
            spProvinsi.setError( "is required!" )
            return  false
        }


        return true
    }
}
