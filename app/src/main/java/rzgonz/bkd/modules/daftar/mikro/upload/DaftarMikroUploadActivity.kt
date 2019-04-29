package rzgonz.bkd.modules.daftar.mikro.upload

import android.Manifest
import android.content.Intent
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.tbruyelle.rxpermissions2.RxPermissions
import id.zelory.compressor.Compressor
import kotlinx.android.synthetic.main.activity_daftar_mikro_upload.*
import kotlinx.android.synthetic.main.header_daftar.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import rzgonz.bkd.Apps.APKModel
import rzgonz.bkd.Apps.compressImage
import rzgonz.bkd.R
import rzgonz.bkd.constant.BKD
import rzgonz.bkd.injector.User.DaggerUserComponent
import rzgonz.bkd.models.LoginResponse
import rzgonz.bkd.models.pinjaman.Content
import rzgonz.bkd.models.user.UserContent
import rzgonz.bkd.modules.daftar.kilat.datadiri.DaftarKilatDataDiriActivity
import rzgonz.bkd.modules.daftar.kilat.upload.adapter.TenorAdapter
import rzgonz.bkd.modules.home.DashboardActivity
import rzgonz.core.kotlin.activity.DIBaseActivity
import rzgonz.core.kotlin.helper.SharedPreferenceService
import java.io.File
import javax.inject.Inject

class DaftarMikroUploadActivity : DIBaseActivity(),DaftarMikroUploadContract.View {
    var imgfoto : File? = null
    var imgNIK : File? = null
    var imgFotoUsaha : File? = null
    var imgFotoUsaha2 : File? = null
    var imgFotoUsaha3 : File? = null
    var imgFotoUsaha4 : File? = null
    var imgFotoUsaha5 : File? = null
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

    private var data: UserContent? = null

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
            if(checkInput()) {
                showProgressDialog(this,"Upload Progress",false)
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
        btnUsaha2.setOnClickListener {
            isfoto = 3
            easyImage()
        }
        btnUsaha3.setOnClickListener {
            isfoto = 4
            easyImage()
        }
        btnUsaha4.setOnClickListener {
            isfoto = 5
            easyImage()
        }
        btnUsaha5.setOnClickListener {
            isfoto = 6
            easyImage()
        }
        mPresenter.getPinjaman()
    }
    private fun isNullOrEmpty(str: String?): Boolean {
        if (str != null && !str.isEmpty())
            return false
        return true
    }
    private fun bindData(data: UserContent?) {
        etNomorNIK.setText("${data?.nomorNik}")
        etRekening.setText("${data?.nomorRekening}")
        etUsaha.setText("${data?.usaha}")
        etLamaUsaha.setText("${data?.lamaUsaha}")
        etUsaha.setText("${data?.usaha}")
        val response = LoginResponse()
        val pekerjaan = resources.getStringArray(R.array.array_pekerjaan)
        val headers = LazyHeaders.Builder().addHeader("Authorization", SharedPreferenceService(baseContext).getString(BKD.TOKEN, response.token)).build()
        pekerjaan.forEachIndexed { index, s ->
            if(index.plus(1).toString().equals(data?.pekerjaan,true)){
                spPekerjaan.setSelection(index)
            }
        }

        if ( isNullOrEmpty(data?.fotoFile )){
        }else{
            val glideUrlfotoFile = GlideUrl(data?.fotoFile, headers)
            Glide.with(this)
                    .applyDefaultRequestOptions(RequestOptions()
                            .placeholder(R.drawable.loading))
                    .asFile()
                    .load(glideUrlfotoFile)
                    .apply(RequestOptions().override(248,248 ))
                    .into(object : SimpleTarget<File>() {
                        override fun onResourceReady(resource: File, transition: Transition<in File>?) {
                            Glide.with(baseContext)
                                    .load(data?.fotoFile)
                                    .apply(RequestOptions()
                                            .override(248,248 ))
                                    .into(imgFoto)
                            imgFoto.visibility = View.VISIBLE
                            llFoto.visibility = View.GONE
                            imgFoto.setOnClickListener {
                                imgfoto = null
                                llFoto.visibility = View.VISIBLE
                                imgFoto.visibility = View.GONE
                            }
                        }

                        override fun onLoadFailed(errorDrawable: Drawable?) {
                            super.onLoadFailed(errorDrawable)
                            imgfoto = null
                            llFoto.visibility = View.VISIBLE
                            imgFoto.visibility = View.GONE
                        }
                    })
        }

        if (isNullOrEmpty(data?.nikFile)) {
        }else{
            val glideUrlnikFile = GlideUrl(data?.nikFile, headers)
            Glide.with(this).applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.loading))
                    .asFile()
                    .load(glideUrlnikFile)
                    .apply(RequestOptions().override(248,248 ))
                    .into(object : SimpleTarget<File>() {
                        override fun onResourceReady(resource: File, transition: Transition<in File>?) {
                            Glide.with(baseContext)
                                    .load(data?.nikFile)
                                    .apply(RequestOptions()
                                            .override(248,248 ))
                                    .into(imgKTP)
                            imgKTP.visibility = View.VISIBLE
                            llNIK.visibility = View.GONE
                            imgKTP.setOnClickListener {
                                imgNIK = null
                                llNIK.visibility = View.VISIBLE
                                imgKTP.visibility = View.GONE
                            }
                        }

                        override fun onLoadFailed(errorDrawable: Drawable?) {
                            super.onLoadFailed(errorDrawable)
                            imgNIK = null
                            llNIK.visibility = View.VISIBLE
                            imgKTP.visibility = View.GONE
                        }
                    })
        }

        if ( isNullOrEmpty(data?.fotoUsahaFile) ) {
        }else{
            val glideUrlfotoUsahaFile = GlideUrl(data?.fotoUsahaFile, headers)
            Glide.with(this).applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.loading))
                    .asFile()
                    .load(glideUrlfotoUsahaFile)
                    .apply(RequestOptions().override(248,248 ))
                    .into(object : SimpleTarget<File>() {
                        override fun onResourceReady(resource: File, transition: Transition<in File>?) {
                            Glide.with(baseContext)
                                    .load(data?.fotoUsahaFile)
                                    .apply(RequestOptions()
                                            .override(248,248 ))
                                    .into(imgUsaha)
                            imgUsaha.visibility = View.VISIBLE
                            llUsaha.visibility = View.GONE
                            imgUsaha.setOnClickListener {
                                imgFotoUsaha = null
                                llUsaha.visibility = View.VISIBLE
                                imgUsaha.visibility = View.GONE
                            }
                        }

                        override fun onLoadFailed(errorDrawable: Drawable?) {
                            super.onLoadFailed(errorDrawable)
                            imgFotoUsaha = null
                            llUsaha.visibility = View.VISIBLE
                            imgUsaha.visibility = View.GONE
                        }
                    })
        }

        if (isNullOrEmpty(data?.fotoUsahaFile2)){
        } else {
            val glideUrlfotoUsahaFile2 = GlideUrl(data?.fotoUsahaFile2, headers)
            Glide.with(this).applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.loading))
                    .asFile()
                    .load(glideUrlfotoUsahaFile2)
                    .apply(RequestOptions().override(248,248 ))
                    .into(object : SimpleTarget<File>() {
                        override fun onResourceReady(resource: File, transition: Transition<in File>?) {
                            Glide.with(baseContext)
                                    .load(data?.fotoUsahaFile2)
                                    .apply(RequestOptions()
                                            .override(248,248 ))
                                    .into(imgUsaha2)
                            imgUsaha2.visibility = View.VISIBLE
                            llUsaha2.visibility = View.GONE
                            imgUsaha2.setOnClickListener {
                                imgFotoUsaha2 = null
                                llUsaha2.visibility = View.VISIBLE
                                imgUsaha2.visibility = View.GONE
                            }
                        }

                        override fun onLoadFailed(errorDrawable: Drawable?) {
                            super.onLoadFailed(errorDrawable)
                            imgFotoUsaha2 = null
                            llUsaha2.visibility = View.VISIBLE
                            imgUsaha2.visibility = View.GONE
                        }
                    })
        }

        if (isNullOrEmpty(data?.fotoUsahaFile3)) {
        }else{
            val glideUrlfotoUsahaFile3 = GlideUrl(data?.fotoUsahaFile3, headers)
            Glide.with(this).applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.loading))
                    .asFile()
                    .load(glideUrlfotoUsahaFile3)
                    .apply(RequestOptions().override(248,248 ))
                    .into(object : SimpleTarget<File>() {
                        override fun onResourceReady(resource: File, transition: Transition<in File>?) {
                            Glide.with(baseContext)
                                    .load(data?.fotoUsahaFile3)
                                    .apply(RequestOptions()
                                            .override(248,248 ))
                                    .into(imgUsaha3)
                            imgUsaha3.visibility = View.VISIBLE
                            llUsaha3.visibility = View.GONE
                            imgUsaha3.setOnClickListener {
                                imgFotoUsaha3 = null
                                llUsaha3.visibility = View.VISIBLE
                                imgUsaha3.visibility = View.GONE
                            }
                        }

                        override fun onLoadFailed(errorDrawable: Drawable?) {
                            super.onLoadFailed(errorDrawable)
                            imgFotoUsaha3 = null
                            llUsaha3.visibility = View.VISIBLE
                            imgUsaha3.visibility = View.GONE
                        }
                    })
        }

        if (isNullOrEmpty(data?.fotoUsahaFile4)) {
        }else{
            val glideUrlfotoUsahaFile4 = GlideUrl(data?.fotoUsahaFile4, headers)
            Glide.with(this).applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.loading))
                    .asFile()
                    .load(glideUrlfotoUsahaFile4)
                    .apply(RequestOptions().override(248,248 ))
                    .into(object : SimpleTarget<File>() {
                        override fun onResourceReady(resource: File, transition: Transition<in File>?) {
                            Glide.with(baseContext)
                                    .load(data?.fotoUsahaFile4)
                                    .apply(RequestOptions()
                                            .override(248,248 ))
                                    .into(imgUsaha4)
                            imgUsaha4.visibility = View.VISIBLE
                            llUsaha4.visibility = View.GONE
                            imgUsaha4.setOnClickListener {
                                imgFotoUsaha4 = null
                                llUsaha4.visibility = View.VISIBLE
                                imgUsaha4.visibility = View.GONE
                            }
                        }

                        override fun onLoadFailed(errorDrawable: Drawable?) {
                            super.onLoadFailed(errorDrawable)
                            imgFotoUsaha4 = null
                            llUsaha4.visibility = View.VISIBLE
                            imgUsaha4.visibility = View.GONE
                        }
                    })
        }

        if ( isNullOrEmpty(data?.fotoUsahaFile5) ) {
        }else{
            val glideUrlfotoUsahaFile5 = GlideUrl(data?.fotoUsahaFile5, headers)
            Glide.with(this).applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.loading))
                    .asFile()
                    .load(glideUrlfotoUsahaFile5)
                    .apply(RequestOptions().override(248,248 ))
                    .into(object : SimpleTarget<File>() {
                        override fun onResourceReady(resource: File, transition: Transition<in File>?) {
                            Glide.with(baseContext)
                                    .load(data?.fotoUsahaFile5)
                                    .apply(RequestOptions()
                                            .override(248,248 ))
                                    .into(imgUsaha5)
                            imgUsaha5.visibility = View.VISIBLE
                            llUsaha5.visibility = View.GONE
                            imgUsaha5.setOnClickListener {
                                imgFotoUsaha5 = null
                                llUsaha5.visibility = View.VISIBLE
                                imgUsaha5.visibility = View.GONE
                            }
                        }

                        override fun onLoadFailed(errorDrawable: Drawable?) {
                            super.onLoadFailed(errorDrawable)
                            imgFotoUsaha5 = null
                            llUsaha5.visibility = View.VISIBLE
                            imgUsaha5.visibility = View.GONE
                        }
                    })
        }
    }

    private fun sendUpload() {
        mPresenter.checkPinjaman()
    }

    override fun onResume() {
        super.onResume()
        checkPermissionAndCreateCamera()
    }

    private fun checkPermissionAndCreateCamera() {
        val rxPermissions = RxPermissions(this)
        rxPermissions
                .requestEach(
                        Manifest.permission.CAMERA
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
        EasyImage.openCamera(this,1)
    }

    override fun returnUpload(status: Boolean, responde: String?, message: String) {
        progressDialog?.dismiss()
        if(status){
            showMessage("Pendaftaran berhasil")
            startActivity(Intent(baseContext, DashboardActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }else{
            showMessage(message)
        }
    }

    override fun retrunPijanam(status: Boolean, content: Content?, message: String) {
        if(status){
            spinnerAdapterTenor = TenorAdapter(baseContext, content?.products!!,true)
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
                            imgfoto = imagesFiles.get(i).compressImage(applicationContext)

                            imgFoto.visibility = View.VISIBLE
                            Glide.with(imgFoto).load(imgfoto).into(imgFoto)
                            llFoto.visibility = View.GONE
                            imgFoto.setOnClickListener {
                                imgfoto = null
                                llFoto.visibility = View.VISIBLE
                                imgFoto.visibility = View.GONE
                            }
                        }
                        1->{

                            imgNIK = imagesFiles.get(i).compressImage(applicationContext)

                            imgKTP.visibility = View.VISIBLE
                            Glide.with(imgKTP).load(imgNIK).into(imgKTP)
                            llNIK.visibility = View.GONE
                            imgKTP.setOnClickListener {
                                imgNIK = null
                                llNIK.visibility = View.VISIBLE
                                imgKTP.visibility = View.GONE
                            }
                        }
                        2->{

                            imgFotoUsaha = imagesFiles.get(i).compressImage(applicationContext)

                            imgUsaha.visibility = View.VISIBLE
                            Glide.with(imgUsaha).load(imgFotoUsaha).into(imgUsaha)
                            llUsaha.visibility = View.GONE
                            imgUsaha.setOnClickListener {
                                imgFotoUsaha = null
                                llUsaha.visibility = View.VISIBLE
                                imgUsaha.visibility = View.GONE
                            }
                        }
                        3->{

                            imgFotoUsaha2 = imagesFiles.get(i).compressImage(applicationContext)

                            imgUsaha2.visibility = View.VISIBLE
                            Glide.with(imgUsaha2).load(imgFotoUsaha2).into(imgUsaha2)
                            llUsaha2.visibility = View.GONE
                            imgUsaha2.setOnClickListener {
                                imgFotoUsaha2 = null
                                llUsaha2.visibility = View.VISIBLE
                                imgUsaha2.visibility = View.GONE
                            }
                        }
                        4->{

                            imgFotoUsaha3 = imagesFiles.get(i).compressImage(applicationContext)

                            imgUsaha3.visibility = View.VISIBLE
                            Glide.with(imgUsaha3).load(imgFotoUsaha3).into(imgUsaha3)
                            llUsaha3.visibility = View.GONE
                            imgUsaha3.setOnClickListener {
                                imgFotoUsaha3 = null
                                llUsaha3.visibility = View.VISIBLE
                                imgUsaha3.visibility = View.GONE
                            }
                        }
                        5->{

                            imgFotoUsaha4 = imagesFiles.get(i).compressImage(applicationContext)

                            imgUsaha4.visibility = View.VISIBLE
                            Glide.with(imgUsaha4).load(imgFotoUsaha4).into(imgUsaha4)
                            llUsaha4.visibility = View.GONE
                            imgUsaha4.setOnClickListener {
                                imgFotoUsaha4 = null
                                llUsaha4.visibility = View.VISIBLE
                                imgUsaha4.visibility = View.GONE
                            }
                        }
                        6->{

                            imgFotoUsaha5 = imagesFiles.get(i).compressImage(applicationContext)

                            imgUsaha5.visibility = View.VISIBLE
                            Glide.with(imgUsaha5).load(imgFotoUsaha5).into(imgUsaha5)
                            llUsaha5.visibility = View.GONE
                            imgUsaha5.setOnClickListener {
                                imgFotoUsaha5 = null
                                llUsaha5.visibility = View.VISIBLE
                                imgUsaha5.visibility = View.GONE
                            }
                        }
                    }
                }
            }
        })
    }

    private fun checkInput(): Boolean {

        if(imgfoto == null && imgFoto.visibility == View.GONE){
            showError("Foto Belum ada")
            return false
        }
        if(imgNIK == null && imgKTP.visibility == View.GONE){
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


        if(imgFotoUsaha == null && imgUsaha.visibility == View.GONE){
            showError("Foto Usaha Belum ada")
            return false
        }

        if(imgFotoUsaha2 == null && imgUsaha2.visibility == View.GONE){
            showError("Foto Usaha2 Belum ada")
            return false
        }

        if(imgFotoUsaha3 == null && imgUsaha3.visibility == View.GONE){
            showError("Foto Usaha3 Belum ada")
            return false
        }

        if(imgFotoUsaha4 == null && imgUsaha4.visibility == View.GONE){
            showError("Foto Usaha4 Belum ada")
            return false
        }

        if(imgFotoUsaha5 == null && imgUsaha5.visibility == View.GONE){
            showError("Foto Usaha5 Belum ada")
            return false
        }

        if(!cbTnC.isChecked){
            showError("Anda Belum Checklist Syarat Dan Ketetuan")
            return false
        }

        return true
    }

    override fun returnCheckPinjaman(status: Boolean, responde: String?, message: String?) {
//        progressDialog?.dismiss()
        if(status){

            val builder = MultipartBody.Builder()
            builder.setType(MultipartBody.FORM)
            builder.addFormDataPart("nomor_rekening", etRekening.text.toString())
            builder.addFormDataPart("jumlah_pinjaman",etPinjaman.text.toString())

            builder.addFormDataPart("nomor_nik",etNomorNIK.text.toString())
            builder.addFormDataPart("lama_usaha",etLamaUsaha.text.toString())
            builder.addFormDataPart("usaha",etUsaha.text.toString())

            builder.addFormDataPart("product_id", spinnerAdapterTenor?.getItem(spTenor.selectedItemPosition)?.productId!!)
            builder.addFormDataPart("pekerjaan",spPekerjaan.selectedItemPosition.plus(1).toString())

            if(imgFotoUsaha != null) {
                val fileUsaha = File(imgFotoUsaha?.path)
                builder.addFormDataPart("foto_usaha", fileUsaha.getName(), RequestBody.create(MediaType.parse("image/*"), fileUsaha))
            }

            if(imgFotoUsaha2 != null) {
                val fileUsaha2 = File(imgFotoUsaha2?.path)
                builder.addFormDataPart("foto_usaha2", fileUsaha2.getName(), RequestBody.create(MediaType.parse("image/*"), fileUsaha2))
            }

            if(imgFotoUsaha3 != null) {
                val fileUsaha3 = File(imgFotoUsaha3?.path)
                builder.addFormDataPart("foto_usaha3", fileUsaha3.getName(), RequestBody.create(MediaType.parse("image/*"), fileUsaha3))
            }

            if(imgFotoUsaha4 != null) {
                val fileUsaha4 = File(imgFotoUsaha4?.path)
                builder.addFormDataPart("foto_usaha4", fileUsaha4.getName(), RequestBody.create(MediaType.parse("image/*"), fileUsaha4))
            }

            if(imgFotoUsaha5 != null) {
                val fileUsaha5 = File(imgFotoUsaha5?.path)
                builder.addFormDataPart("foto_usaha5", fileUsaha5.getName(), RequestBody.create(MediaType.parse("image/*"), fileUsaha5))
            }

            if(imgfoto != null){
                val fileFoto = File(imgfoto?.path)
                builder.addFormDataPart("foto_file", fileFoto.getName(), RequestBody.create(MediaType.parse("image/*"), fileFoto))
            }

            if (imgNIK != null){
                val fileNik = File(imgNIK?.path)
                builder.addFormDataPart("nik_file", fileNik.getName(), RequestBody.create(MediaType.parse("image/*"), fileNik))
            }

            val requestBody:RequestBody = builder.build()
            mPresenter.sendUpload(requestBody)
        }else{
            showError(message)
            startActivity(Intent(this, DashboardActivity::class.java))
        }
    }

}
