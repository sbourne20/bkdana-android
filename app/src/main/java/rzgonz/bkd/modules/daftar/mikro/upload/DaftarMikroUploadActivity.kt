package rzgonz.bkd.modules.daftar.mikro.upload

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_daftar_mikro_upload.*
import kotlinx.android.synthetic.main.header_daftar.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import rzgonz.bkd.Apps.APKModel
import rzgonz.bkd.R
import rzgonz.bkd.injector.User.DaggerUserComponent
import rzgonz.bkd.models.pinjaman.Content
import rzgonz.bkd.modules.daftar.kilat.upload.adapter.TenorAdapter
import rzgonz.bkd.modules.home.DashboardActivity
import rzgonz.core.kotlin.activity.DIBaseActivity
import java.io.File
import javax.inject.Inject

class DaftarMikroUploadActivity : DIBaseActivity(),DaftarMikroUploadContract.View {
    var imgfoto : File? = null
    var imgNIK : File? = null
    var imgFotoUsaha : File? = null
    var isfoto = 0

    @Inject
    lateinit var mPresenter : DaftarMikroUploadPresenter

    var spinnerAdapterTenor: TenorAdapter? = null

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
        return R.layout.activity_daftar_mikro_upload
    }
    override fun initUI(savedInstanceState: Bundle?) {
        collapsing_toolbar.isTitleEnabled = false
        collapsing_toolbar.title = "Daftat BKDana Mikro"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Daftar BKDana Mikro")
        btnSelanjutnya.setOnClickListener {
            if(checkInput()) {
                showProgressDialog(this,"Upload Progress",true)
                sendUpload()
            }
        }
        btnFoto.setOnClickListener {
            isfoto = 0
            easyImage()
        }

        btnNIK.setOnClickListener {
            isfoto = 1
            easyImage()
        }
        btnUsaha.setOnClickListener {
            isfoto = 2
            easyImage()
        }
        mPresenter.getPinjaman()
    }

    private fun sendUpload() {
        val fileFoto = File(imgfoto?.path)
        val fileNik = File(imgNIK?.path)
        val fileUsaha = File(imgFotoUsaha?.path)

        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)
        builder.addFormDataPart("nomor_rekening", etRekening.text.toString())
        builder.addFormDataPart("jumlah_pinjaman",etPinjaman.text.toString())
        builder.addFormDataPart("product_id", spinnerAdapterTenor?.getItem(spTenor.selectedItemPosition)?.productId!!)
        builder.addFormDataPart("foto_usaha",fileUsaha.getName(), RequestBody.create(MediaType.parse("image/*"), fileUsaha))
        builder.addFormDataPart("foto_file", fileFoto.getName(), RequestBody.create(MediaType.parse("image/*"), fileFoto))
        builder.addFormDataPart("nik_file", fileNik.getName(), RequestBody.create(MediaType.parse("image/*"), fileNik))
        val requestBody:RequestBody = builder.build()
        mPresenter.sendUpload(requestBody)
    }

    override fun onResume() {
        super.onResume()
        checkPermissionAndCreateCamera()
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

    fun easyImage(){
        EasyImage.openChooserWithGallery(this,"Pilih untuk cari gambar",1)
    }

    override fun returnUpload(status: Boolean, responde: String?, message: String) {
        progressDialog?.dismiss()
        if(status){
            showMessage("Pendaftaran berhasil")
            startActivity(Intent(baseContext, DashboardActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }
    }

    override fun retrunPijanam(status: Boolean, content: Content?, message: String) {
        Log.d("berak","${content}")
        if(status){
            spinnerAdapterTenor = TenorAdapter(baseContext, content?.products!!)
            spTenor.adapter = spinnerAdapterTenor
        }
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
                    when(isfoto){
                        0 ->{
                            imgfoto = imagesFiles.get(i)
                            imgFoto.visibility = View.VISIBLE
                            Glide.with(imgFoto).load(imagesFiles[i]).into(imgFoto)
                            llFoto.visibility = View.GONE
                            imgFoto.setOnClickListener {
                                imgfoto = null
                                llFoto.visibility = View.VISIBLE
                                imgFoto.visibility = View.GONE
                            }
                        }
                        1->{
                            imgNIK = imagesFiles.get(i)
                            imgKTP.visibility = View.VISIBLE
                            Glide.with(imgKTP).load(imagesFiles[i]).into(imgKTP)
                            llNIK.visibility = View.GONE
                            imgKTP.setOnClickListener {
                                imgNIK = null
                                llNIK.visibility = View.VISIBLE
                                imgKTP.visibility = View.GONE
                            }
                        }
                        2->{
                            imgFotoUsaha = imagesFiles.get(i)
                            imgUsaha.visibility = View.VISIBLE
                            Glide.with(imgUsaha).load(imagesFiles[i]).into(imgUsaha)
                            llUsaha.visibility = View.GONE
                            imgUsaha.setOnClickListener {
                                imgfoto = null
                                llUsaha.visibility = View.VISIBLE
                                imgUsaha.visibility = View.GONE
                            }
                        }
                    }
                }
            }
        })
    }

    private fun checkInput(): Boolean {

        if(imgfoto == null){
            showError("Foto Belum ada")
            return false
        }
        if(imgNIK == null){
            showError("Foto KTP Belum ada")
            return false
        }

        if(etNomorNIK.text.isNullOrEmpty()){
            etNomorNIK.setError( "is required!" )
            return  false
        }
        if(etRekening.text.isNullOrEmpty()){
            etRekening.setError( "is required!" )
            return  false
        }


        if(etUsaha.text.isNullOrEmpty()){
            etUsaha.setError( "is required!" )
            return  false
        }

        if(etLamaUsaha.text.isNullOrEmpty()){
            etLamaUsaha.setError( "is required!" )
            return  false
        }


        if(etPinjaman.text.isNullOrEmpty()){
            etPinjaman.setError( "is required!" )
            return  false
        }


        if(imgFotoUsaha == null){
            showError("Foto Surat Kerja Belum ada")
            return false
        }


        if(!cbTnC.isChecked){
            showError("Ada Belum Checklist Syarat Dan Ketetuan")
            return false
        }

        return true
    }

}