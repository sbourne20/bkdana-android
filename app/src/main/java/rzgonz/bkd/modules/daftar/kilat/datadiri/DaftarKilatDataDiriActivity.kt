package rzgonz.bkd.modules.daftar.kilat.datadiri

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_daftar_kilat_data_diri.*
import kotlinx.android.synthetic.main.header_daftar.*
import rzgonz.bkd.Apps.APKModel
import rzgonz.bkd.R
import rzgonz.bkd.injector.User.DaggerUserComponent
import rzgonz.bkd.models.user.Content
import rzgonz.bkd.modules.daftar.kilat.upload.DaftarKilatUploadActivity
import rzgonz.core.kotlin.activity.DIBaseActivity
import javax.inject.Inject

class DaftarKilatDataDiriActivity : DIBaseActivity(),DaftarKilatDataDiriContract.View {

    @Inject
    lateinit var mPresenter : DaftarKilatDataDiriPresenter

    companion object {
        var extra_data ="extra_data"
        @JvmStatic
        fun startThisActivity(context: Context, data:Content) {
            context.startActivity(Intent(context, DaftarKilatDataDiriActivity::class.java).apply {
                putExtra(extra_data,data)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            })
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

    override fun initLayout(): Int {
       return R.layout.activity_daftar_kilat_data_diri
    }

    override fun initUI(savedInstanceState: Bundle?) {
        collapsing_toolbar.isTitleEnabled = false
        collapsing_toolbar.title = "Daftat BKDana Kilat"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Daftar BKDana Kilat")
        if(intent.hasExtra(extra_data)){
            val data = intent.getParcelableExtra<Content>(extra_data)
            bindData(data)
        }

        btnSelanjutnya.setOnClickListener {
            sendDataDiri()
        }
    }

    private fun sendDataDiri() {
        if(inputOK()) {
            showProgressDialog(this,"Upload Progress",true)
            mPresenter.sendDataDiri(spPendidikan.selectedItem.toString(), etPerusahaan.text.toString(), etPhoneKantor.text.toString(), spKaryaan.selectedItem.toString(), etLamaBekerja.text.toString(), etNameAtasan.text.toString(), etReferensi.text.toString(), etReferensiSub.text.toString())
        }
    }

    private fun inputOK(): Boolean {

        if(etPerusahaan.text.isNullOrEmpty()){
            etPerusahaan.setError( "is required!" )
            return  false
        }
        if(etPhoneKantor.text.isNullOrEmpty()){
            etPhoneKantor.setError( "is required!" )
            return  false
        }

        if(etLamaBekerja.text.isNullOrEmpty()){
            etLamaBekerja.setError( "is required!" )
            return  false
        }

        if(etNameAtasan.text.isNullOrEmpty()){
            etNameAtasan.setError( "is required!" )
            return  false
        }

        if(etReferensi.text.isNullOrEmpty()){
            etReferensi.setError( "is required!" )
            return  false
        }

        if(etReferensiSub.text.isNullOrEmpty()){
            etReferensiSub.setError( "is required!" )
            return  false
        }



        return true
    }

    private fun bindData(data: Content) {
        Log.d("BIND","${data}")
        spPendidikan.setSelection(data.pendidikan!!.toInt())
        etLamaBekerja.setText("${data.lamaBekerja}")

    }

    override fun returnSendDataDiri(status: Boolean, responde: String?, message: String) {
        progressDialog?.dismiss()
        if(status){
            startActivity(Intent(this, DaftarKilatUploadActivity::class.java))
        }

    }
}
