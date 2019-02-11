package rzgonz.bkd.modules.daftar.mikro.usaha

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_daftar_mikro_usaha.*
import kotlinx.android.synthetic.main.header_daftar.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import rzgonz.bkd.Apps.APKModel
import rzgonz.bkd.R
import rzgonz.bkd.injector.User.DaggerUserComponent
import rzgonz.bkd.models.user.UserContent
import rzgonz.bkd.modules.daftar.kilat.datadiri.DaftarKilatDataDiriActivity
import rzgonz.bkd.modules.daftar.kilat.upload.DaftarKilatUploadActivity
import rzgonz.bkd.modules.daftar.mikro.upload.DaftarMikroUploadActivity
import rzgonz.core.kotlin.activity.DIBaseActivity
import java.io.File
import javax.inject.Inject

class DaftarMirkoUsahaActivity : DIBaseActivity(),DaftarMikroUsahaContract.View {
    var imgfoto : File? = null

    companion object {
        var extra_data ="extra_data"
        @JvmStatic
        fun startThisActivity(context: Context, data: UserContent) {
            context.startActivity(Intent(context, DaftarMirkoUsahaActivity::class.java).apply {
                putExtra(extra_data,data)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            })
        }
    }
    @Inject
    lateinit var mPresenter : DaftarMikroUsahaPresenter

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
      return R.layout.activity_daftar_mikro_usaha
    }

    override fun initUI(savedInstanceState: Bundle?) {
        collapsing_toolbar.isTitleEnabled = false
        collapsing_toolbar.title = "Daftat BKDana Mikro"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Daftar BKDana Mikro")
        if(intent.hasExtra(DaftarKilatDataDiriActivity.extra_data)){
            val data = intent.getParcelableExtra<UserContent>(DaftarKilatDataDiriActivity.extra_data)
            bindData(data)
        }

        btnSelanjutnya.setOnClickListener {
            if(checkInput()){
                showProgressDialog(this,"Upload Progress",true)
                sendDataUsaha()
            }
        }

        btnInfoUsaha.setOnClickListener {
                easyImage()
        }
    }

    private fun bindData(data: UserContent?) {
        Log.d("BIND","${data}")
        etDeskripsi.setText("${data?.deskripsi_usaha}")
        etOmzet.setText("${data?.omzet_usaha}")
        etMargin.setText("${data?.margin_usaha}")
        etLaba.setText("${data?.laba_usaha}")
        etBunga.setText("${data?.jml_bunga_usaha}")
        etOperasional.setText("${data?.biaya_operasional_usaha}")

    }

    fun easyImage(){
        EasyImage.openCamera(this,1)
    }

    override fun returnDataUsaha(status: Boolean, responde: String?, message: String) {
        progressDialog?.dismiss()
        if(status){
            showMessage("Pendaftaran berhasil")
            startActivity(Intent(baseContext,DaftarMikroUploadActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }
    }

    fun sendDataUsaha(){
        val fileFoto = File(imgfoto?.path)
        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)
        builder.addFormDataPart("deskripsi_usaha", etDeskripsi.text.toString())
        builder.addFormDataPart("info_usaha_file",fileFoto.getName(), RequestBody.create(MediaType.parse("image/*"), fileFoto))
        builder.addFormDataPart("omzet", etOmzet.text.toString())
        builder.addFormDataPart("margin", etMargin.text.toString())
        builder.addFormDataPart("biaya_operasional",etOperasional.text.toString())
        builder.addFormDataPart("laba_usaha", etLaba.text.toString())
        builder.addFormDataPart("jml_bunga", etBunga.text.toString())
        builder.addFormDataPart("nama_bank",spBank.selectedItem.toString())
        val requestBody:RequestBody = builder.build()
        mPresenter.sendDataUsaha(requestBody)
    }

    private fun checkInput(): Boolean {

        if(imgfoto == null){
            showError("Foto Belum ada")
            return false
        }

        if(etDeskripsi.text.isNullOrEmpty()){
            etDeskripsi.setError( "is required!" )
            return  false
        }

        if(etOmzet.text.isNullOrEmpty()){
            etOmzet.setError( "is required!" )
            return  false
        }
        if(etMargin.text.isNullOrEmpty()){
            etMargin.setError( "is required!" )
            return  false
        }
        if(etOperasional.text.isNullOrEmpty()){
            etOperasional.setError( "is required!" )
            return  false
        }
        if(etLaba.text.isNullOrEmpty()){
            etLaba.setError( "is required!" )
            return  false
        }
        if(etBunga.text.isNullOrEmpty()){
            etBunga.setError( "is required!" )
            return  false
        }

        if(!cbTnC.isChecked){
            showError("Ada Belum Checklist Syarat Dan Ketentua")
            return false
        }

        return true
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, object : DefaultCallback() {

            override fun onImagePickerError(e: Exception?, source: EasyImage.ImageSource?, type: Int) {
                //Some error handling
            }

            override fun onImagesPicked(imagesFiles: List<File>, source: EasyImage.ImageSource, type: Int) {
                //Handle the images
                for (i in 0..imagesFiles.size) {
                    imgfoto = imagesFiles.get(i)
                    imgInfoUsaha.visibility = View.VISIBLE
                    Glide.with(imgInfoUsaha).load(imagesFiles[i]).into(imgInfoUsaha)
                    llUsaha.visibility = View.GONE
                    imgInfoUsaha.setOnClickListener {
                        imgfoto = null
                        llUsaha.visibility = View.VISIBLE
                        imgInfoUsaha.visibility = View.GONE
                    }
                    }
            }
        })
    }
}
