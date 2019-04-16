package rzgonz.bkd.modules.daftar.mikro.usaha

import android.Manifest
import android.content.Context
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_daftar_mikro_usaha.*
import kotlinx.android.synthetic.main.header_daftar.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import rzgonz.bkd.Apps.APKModel
import rzgonz.bkd.Apps.compressImage
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

    private var data: UserContent? =null

    override fun initUI(savedInstanceState: Bundle?) {
        collapsing_toolbar.isTitleEnabled = false
        collapsing_toolbar.title = "Daftat BKDana Mikro"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Daftar BKDana Mikro")
        if(intent.hasExtra(DaftarKilatDataDiriActivity.extra_data)){
            data = intent.getParcelableExtra<UserContent>(DaftarKilatDataDiriActivity.extra_data)
            bindData(data)
        }

        btnSelanjutnya.setOnClickListener {
            if(checkInput()){
                showProgressDialog(this,"Upload Progress",false)
                sendDataUsaha()
            }
        }

        btnInfoUsaha.setOnClickListener {
                easyImage()
        }
    }

    private fun bindData(data: UserContent?) {
        Log.d("BIND","${data}")
        etDeskripsi.setText("${data?.deskripsiUsaha}")
        etOmzet.setText("${data?.omzetUsaha}")
        etMargin.setText("${data?.marginUsaha}")
        etLaba.setText("${data?.labaUsaha}")
     //   etModal.setText("${data?.biayaOperasionalUsaha}")
       // etBunga.setText("${data?.jml_bunga_usaha}")
        etOperasional.setText("${data?.biayaOperasionalUsaha}")
        val bank = resources.getStringArray(R.array.array_bank)
        bank.forEachIndexed { index, s ->
            if(s.equals(data?.namaBank,true)){
                spBank.setSelection(index)
            }
        }
        Glide.with(this).applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.loading)).asFile().load(data?.fotoUsahaFile).into(object : SimpleTarget<File>() {
            override fun onResourceReady(resource: File, transition: Transition<in File>?) {
                imgInfoUsaha.setImageURI(Uri.fromFile(resource))
                imgfoto = resource
                imgInfoUsaha.visibility = View.VISIBLE
                llUsaha.visibility = View.GONE
                imgInfoUsaha.setOnClickListener {
                    imgfoto = null
                    llUsaha.visibility = View.VISIBLE
                    imgInfoUsaha.visibility = View.GONE
                }
            }

            override fun onLoadFailed(errorDrawable: Drawable?) {
                super.onLoadFailed(errorDrawable)
                imgfoto = null
                llUsaha.visibility = View.VISIBLE
                imgInfoUsaha.visibility = View.GONE
            }
        })

    }

    override fun onResume() {
        super.onResume()
        checkPermissionAndCreateCamera()
    }


    fun easyImage(){
        EasyImage.openCamera(this,1)
    }

    override fun returnDataUsaha(status: Boolean, responde: String?, message: String) {
        progressDialog?.dismiss()
        if(status){
           // showMessage("Pendaftaran berhasil")
            startActivity(Intent(baseContext,DaftarMikroUploadActivity::class.java).putExtra(DaftarKilatDataDiriActivity.extra_data,data).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }else{
            showError(message)
        }
    }
    private fun checkPermissionAndCreateCamera() {
        val rxPermissions = RxPermissions(this)
        rxPermissions
                .requestEach(
                        Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                )
                .subscribe { // will emit 2 Permission objects
                    permission ->
                    if (permission.granted) {

                    } else if (permission.shouldShowRequestPermissionRationale) {
                        checkPermissionAndCreateCamera()
                    } else {
                        goToPermissionSettings(baseContext)
                    }
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
//        if(etBunga.text.isNullOrEmpty()){
//            etBunga.setError( "is required!" )
//            return  false
//        }

        if(spBank.selectedItemPosition < 1){
            showError("Bank Belum Dipilih")
            return false
        }
//        if(!cbTnC.isChecked){
//            showError("Ada Belum Checklist Syarat Dan Ketentua")
//            return false
//        }

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
                    imgfoto = imagesFiles.get(i).compressImage(applicationContext)
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
