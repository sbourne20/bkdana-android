package rzgonz.bkd.modules.daftar.kilat.upload

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
import com.jakewharton.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_daftar_kilat_upload.*
import kotlinx.android.synthetic.main.header_daftar.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import retrofit2.Response
import rzgonz.bkd.Apps.APKModel
import rzgonz.bkd.Apps.compressImage
import rzgonz.bkd.R
import rzgonz.bkd.constant.BKD
import rzgonz.bkd.injector.User.DaggerUserComponent
import rzgonz.bkd.models.LoginResponse
import rzgonz.bkd.models.pinjaman.Content
import rzgonz.bkd.models.user.UserContent
import rzgonz.bkd.modules.daftar.kilat.datadiri.DaftarKilatDataDiriActivity
import rzgonz.bkd.modules.daftar.kilat.upload.adapter.PinjamanAdapter
import rzgonz.bkd.modules.daftar.kilat.upload.adapter.TenorAdapter
import rzgonz.bkd.modules.home.DashboardActivity
import rzgonz.bkd.modules.home.DashboardFragmentPresenter
import rzgonz.core.kotlin.activity.DIBaseActivity
import rzgonz.core.kotlin.helper.SharedPreferenceService
import java.io.*
import javax.inject.Inject

class DaftarKilatUploadActivity : DIBaseActivity(),DaftarKilatUploadContract.View {

    var isfoto = 0
    var imgfoto : File? = null
    var imgNIK : File? = null
    var imgkerja : File? = null
    var imggaji : File? = null
    var imgselfi : File? = null

    var spinnerAdapter:PinjamanAdapter? = null
    var spinnerAdapterTenor:TenorAdapter? = null

    @Inject
    lateinit var mPresenter : DaftarKilatUploadPresenter

    var response = LoginResponse()

    override fun initLayout(): Int {
        return R.layout.activity_daftar_kilat_upload
    }

    private var data: UserContent?= null

    override fun initUI(savedInstanceState: Bundle?) {
        collapsing_toolbar.isTitleEnabled = false
        collapsing_toolbar.title = "Daftat BKDana Kilat"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Daftar BKDana Kilat")
        btnSelanjutnya.setOnClickListener {
            if(checkInput()){
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
        btnSuratKerja.setOnClickListener {
            isfoto = 2
            easyImage()
        }
        btnSlipGaji.setOnClickListener {
            isfoto = 3
            easyImage()
        }
        btnSelfiKtp.setOnClickListener {
            isfoto = 4
            easyImage()
        }
        if(intent.hasExtra(DaftarKilatDataDiriActivity.extra_data)){
            data = intent.getParcelableExtra<UserContent>(DaftarKilatDataDiriActivity.extra_data)

            bindData(data)
        }
        mPresenter.getPinjaman()
    }


   private fun isNullOrEmpty(str: String?): Boolean {
        if (str != null && !str.isEmpty())
            return false
        return true
    }

    private fun bindData(data: UserContent?) {
        etRekening.setText("${data?.nomorRekening}")
        etGaji.setText("${data?.gaji}")
        val headers = LazyHeaders.Builder().addHeader("Authorization", SharedPreferenceService(baseContext).getString(BKD.TOKEN, response.token)).build()

        if(isNullOrEmpty(data?.fotoFile)){
        }else{
            val glideUrlfotoFile = GlideUrl(data?.fotoFile, headers)
            Glide.with(this).applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.loading))
                    .asFile()
//                    .apply(RequestOptions().override(248, 248))
                    .load(glideUrlfotoFile)
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

        if(isNullOrEmpty(data?.nikFile)){
        }else{
            val glideUrlnikFile = GlideUrl(data?.nikFile, headers)
            Glide.with(this).applyDefaultRequestOptions(RequestOptions().placeholder(R.drawable.loading))
                    .asFile()
//                    .apply(RequestOptions().override(248, 248))
                    .load(glideUrlnikFile)
                    .into(object : SimpleTarget<File>() {
                        override fun onResourceReady(resource: File, transition: Transition<in File>?) {
//                            imgKTP.setImageURI(Uri.fromFile(resource))
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
        if(isNullOrEmpty(data?.fotoSuratKetKerja)){
        }else{
            val glideUrlfotoSuratKetKerja = GlideUrl(data?.fotoSuratKetKerja, headers)
            Glide.with(this)
                    .asFile()
                    .apply(RequestOptions().override(248, 248))
                    .load(glideUrlfotoSuratKetKerja)
                    .into(object : SimpleTarget<File>() {

                        override fun onResourceReady(resource: File, transition: Transition<in File>?) {
//                            imgSuratKerja.setImageURI(Uri.fromFile(resource))
                            Glide.with(baseContext)
                                    .load(data?.fotoSuratKetKerja)
                                    .apply(RequestOptions()
                                            .override(248,248 ))
                                    .into(imgSuratKerja)
                            imgSuratKerja.visibility = View.VISIBLE
                            llKerja.visibility = View.GONE
                            imgSuratKerja.setOnClickListener {
                                imgkerja = null
                                llKerja.visibility = View.VISIBLE
                                imgSuratKerja.visibility = View.GONE
                            }
                        }

                        override fun onLoadFailed(errorDrawable: Drawable?) {
                            super.onLoadFailed(errorDrawable)
                            imgkerja = null
                            llKerja.visibility = View.VISIBLE
                            imgSuratKerja.visibility = View.GONE
                        }
                    })
        }
        if(isNullOrEmpty(data?.fotoSlipGaji)){
        }else{
            val glideUrlfotoSlipGaji = GlideUrl(data?.fotoSlipGaji, headers)
            Glide.with(this)
                    .asFile()
                    .apply(RequestOptions().override(248, 248))
                    .load(glideUrlfotoSlipGaji)
                    .into(object : SimpleTarget<File>() {

                        override fun onResourceReady(resource: File, transition: Transition<in File>?) {
//                            imgSlipGaji.setImageURI(Uri.fromFile(resource))
                            Glide.with(baseContext)
                                    .load(data?.fotoSlipGaji)
                                    .apply(RequestOptions()
                                            .override(248,248 ))
                                    .into(imgSlipGaji)

                            imgSlipGaji.visibility = View.VISIBLE
                            llGaji.visibility = View.GONE
                            imgSlipGaji.setOnClickListener {
                                imggaji = null
                                llGaji.visibility = View.VISIBLE
                                imgSlipGaji.visibility = View.GONE
                            }
                        }

                        override fun onLoadFailed(errorDrawable: Drawable?) {
                            super.onLoadFailed(errorDrawable)
                            imggaji = null
                            llGaji.visibility = View.VISIBLE
                            imgSlipGaji.visibility = View.GONE
                        }
                    })
        }

        if(isNullOrEmpty(data?.fotoPegangIdcard)){
        } else {
            val glideUrlfotoPegangId = GlideUrl(data?.fotoPegangIdcard, headers)
            Glide.with(this)
                    .asFile()
                    .apply(RequestOptions().override(248, 248))
                    .load(glideUrlfotoPegangId)
                    .into(object : SimpleTarget<File>() {
                        override fun onResourceReady(resource: File, transition: Transition<in File>?) {
//                            imgSelfiKtp.setImageURI(Uri.fromFile(resource))
                            Glide.with(baseContext)
                                    .load(data?.fotoPegangIdcard)
                                    .apply(RequestOptions()
                                            .override(248,248 ))
                                    .into(imgSelfiKtp)
                            imgSelfiKtp.visibility = View.VISIBLE
                            llSelfi.visibility = View.GONE
                            imgSelfiKtp.setOnClickListener {
                                imgselfi = null
                                llSelfi.visibility = View.VISIBLE
                                imgSelfiKtp.visibility = View.GONE
                            }
                        }

                        override fun onLoadFailed(errorDrawable: Drawable?) {
                            super.onLoadFailed(errorDrawable)
                            imgselfi = null
                            llSelfi.visibility = View.VISIBLE
                            imgSelfiKtp.visibility = View.GONE
                        }
                    })
        }
    }

    fun easyImage(){
        EasyImage.openCamera(this,1)
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

        if(etRekening.text.isNullOrEmpty()){
            etRekening.setError( "is required!" )
            return  false
        }

        if(etGaji.text.isNullOrEmpty()){
            etGaji.setError( "is required!" )
            return  false
        }



        if(imgkerja == null && imgSuratKerja.visibility == View.GONE){
            showError("Foto Surat Kerja Belum ada")
            return false
        }

        if(imggaji == null && imgSlipGaji.visibility == View.GONE){
            showError("Foto Slip Gaji Belum ada")
            return false
        }

        if(imgselfi == null && imgSelfiKtp.visibility == View.GONE){
            showError("Foto Belum ada")
            return false
        }

        if(!cbTnC.isChecked){
            showError("Anda Belum Checklist Syarat Dan Ketentuan")
            return false
        }

        return true
    }


    private fun sendUpload() {
        mPresenter.checkPinjaman()
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
        checkPermissionAndCreateCamera()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, object : DefaultCallback() {

            override fun onImagePickerError(e: Exception?, source: EasyImage.ImageSource?, type: Int) {
                //Some error handling
            }

            override fun onImagesPicked(imagesFiles: List<File>, source: EasyImage.ImageSource, type: Int) {
                //Handle the images

//
                for (i in 0..imagesFiles.size) {
                    when(isfoto){
                        0 ->{
                            imgfoto = imagesFiles.get(i).compressImage(applicationContext)

                            imgFoto.visibility = View.VISIBLE
                            llFoto.visibility = View.GONE
                            imgFoto.setOnClickListener {
                                imgfoto = null
                                llFoto.visibility = View.VISIBLE
                                imgFoto.visibility = View.GONE
                            }
                            Glide.with(imgFoto).load(imgfoto)
                                    .apply(RequestOptions().override(248,248 ))
                                    .into(imgFoto)

                        }
                        1->{
                            imgNIK = imagesFiles.get(i).compressImage(applicationContext)
                            imgKTP.visibility = View.VISIBLE
                            llNIK.visibility = View.GONE
                            imgKTP.setOnClickListener {
                                imgNIK = null
                                llNIK.visibility = View.VISIBLE
                                imgKTP.visibility = View.GONE
                            }
                            Glide.with(imgKTP).load(imgNIK)
                                    .apply(RequestOptions().override(248,248 ))
                                    .into(imgKTP)
                        }
                        2->{

                            imgkerja = imagesFiles.get(i).compressImage(applicationContext)

                            imgSuratKerja.visibility = View.VISIBLE
                            Glide.with(imgSuratKerja).load(imgkerja)
                                    .apply(RequestOptions().override(248,248 ))
                                    .into(imgSuratKerja)
                            llKerja.visibility = View.GONE
                            imgSuratKerja.setOnClickListener {
                                imgkerja = null
                                llKerja.visibility = View.VISIBLE
                                imgSuratKerja.visibility = View.GONE
                            }
                        }
                        3->{

                            imggaji = imagesFiles.get(i).compressImage(applicationContext)
                            imgSlipGaji.visibility = View.VISIBLE
                            Glide.with(imgSlipGaji).load(imggaji)
                                    .apply(RequestOptions().override(248,248 ))
                                    .into(imgSlipGaji)
                            llGaji.visibility = View.GONE
                            imgSlipGaji.setOnClickListener {
                                imggaji = null
                                llGaji.visibility = View.VISIBLE
                                imgSlipGaji.visibility = View.GONE
                            }
                        }
                        4->{

                            imgselfi = imagesFiles.get(i).compressImage(applicationContext)
                            imgSelfiKtp.visibility = View.VISIBLE
                            Glide.with(imgSelfiKtp).load(imgselfi)
                                    .apply(RequestOptions().override(248,248 ))
                                    .into(imgSelfiKtp)
                            llSelfi.visibility = View.GONE
                            imgSelfiKtp.setOnClickListener {
                                imgselfi = null
                                llSelfi.visibility = View.VISIBLE
                                imgSelfiKtp.visibility = View.GONE
                            }
                        }

                    }
                }
            }
        })
    }

    override fun retrunPijanam(status: Boolean, content: Content?, message: String) {

        Log.d("PinjamanReturn","${content}")
        if(status){
             spinnerAdapter = PinjamanAdapter(baseContext, content?.pinjaman!!)
            spPinjaman.adapter = spinnerAdapter
             spinnerAdapterTenor = TenorAdapter(baseContext, content.products!!)
            spTenor.adapter = spinnerAdapterTenor
        }
    }

    override fun returnSendUpload(status: Boolean, responde: String?, message: String) {
        progressDialog?.dismiss()
       if(status){
           showMessage("Pendaftaran berhasil")
           startActivity(Intent(baseContext,DashboardActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP))
       }else{
           showError(message)
       }
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

    override fun returnCheckPinjaman(status: Boolean, responde: String?, message: String?) {
//        progressDialog?.dismiss()
        if(status){
            val builder = MultipartBody.Builder()

            builder.setType(MultipartBody.FORM)
            builder.addFormDataPart("nomor_rekening", etRekening.text.toString())
            builder.addFormDataPart("jumlah_pinjaman",spinnerAdapter?.getItem(spPinjaman.selectedItemPosition)?.nominalPinjaman!!)
            builder.addFormDataPart("product_id", spinnerAdapterTenor?.getItem(spTenor.selectedItemPosition)?.productId!!)
            builder.addFormDataPart("gaji", etGaji.text.toString())
            if(imgkerja != null) {
                val fileKeja = File(imgkerja?.path)
                builder.addFormDataPart("foto_surat_ket_kerja", fileKeja.getName(), RequestBody.create(MediaType.parse("image/*"), fileKeja))
            }
            if(imgfoto != null){
                val fileFoto = File(imgfoto?.path)
                builder.addFormDataPart("foto_file", fileFoto.getName(), RequestBody.create(MediaType.parse("image/*"), fileFoto))
            }
            if(imgNIK != null){
                val fileNik = File(imgNIK?.path)
                builder.addFormDataPart("nik_file", fileNik.getName(), RequestBody.create(MediaType.parse("image/*"), fileNik))
            }
            if(imggaji != null){
                val fileGaji = File(imggaji?.path)
                builder.addFormDataPart("foto_slip_gaji",fileGaji.getName(), RequestBody.create(MediaType.parse("image/*"), fileGaji))
            }
            if(imgselfi != null){
                val fileSelfi = File(imgselfi?.path)
                builder.addFormDataPart("foto_pegang_idcard", fileSelfi.getName(), RequestBody.create(MediaType.parse("image/*"), fileSelfi))
            }
            val requestBody:RequestBody = builder.build()
            mPresenter.sendUpload(requestBody)
        }else{
            showError(message)
            startActivity(Intent(this, DashboardActivity::class.java))
        }
    }

}
