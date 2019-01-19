package rzgonz.bkd.modules.daftar.kilat.upload

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import com.bumptech.glide.Glide
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_daftar_kilat_upload.*
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
import rzgonz.bkd.modules.daftar.kilat.upload.adapter.PinjamanAdapter
import rzgonz.bkd.modules.daftar.kilat.upload.adapter.TenorAdapter
import rzgonz.bkd.modules.home.DashboardActivity
import rzgonz.core.kotlin.activity.DIBaseActivity
import java.io.File
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

    override fun initLayout(): Int {
        return R.layout.activity_daftar_kilat_upload
    }

    override fun initUI(savedInstanceState: Bundle?) {
        collapsing_toolbar.isTitleEnabled = false
        collapsing_toolbar.title = "Daftat BKDana Kilat"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Daftar BKDana Kilat")
        btnSelanjutnya.setOnClickListener {
            if(checkInput()){
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
        mPresenter.getPinjaman()
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

        if(etRekening.text.isNullOrEmpty()){
            etRekening.setError( "is required!" )
            return  false
        }

        if(etGaji.text.isNullOrEmpty()){
            etGaji.setError( "is required!" )
            return  false
        }



        if(imgkerja == null){
            showError("Foto Surat Kerja Belum ada")
            return false
        }

        if(imggaji == null){
            showError("Foto Slip Gaji Belum ada")
            return false
        }

        if(imgfoto == null){
            showError("Foto Belum ada")
            return false
        }

        if(!cbTnC.isChecked){
            showError("Ada Belum Checklist Syarat Dan Ketentua")
            return false
        }

        return true
    }


    private fun sendUpload() {
        val fileFoto = File(imgfoto?.path)
        val fileNik = File(imgNIK?.path)
        val fileKeja = File(imgkerja?.path)
        val fileGaji = File(imggaji?.path)
        val fileSelfi = File(imgselfi?.path)

        val builder = MultipartBody.Builder()
        builder.setType(MultipartBody.FORM)
         builder.addFormDataPart("nomor_rekening", etRekening.text.toString())
        builder.addFormDataPart("jumlah_pinjaman",spinnerAdapter?.getItem(spPinjaman.selectedItemPosition)?.nominalPinjaman!!)
        builder.addFormDataPart("product_id", spinnerAdapterTenor?.getItem(spTenor.selectedItemPosition)?.productId!!)
        builder.addFormDataPart("gaji", etGaji.text.toString())
        builder.addFormDataPart("foto_surat_ket_kerja",fileKeja.getName(), RequestBody.create(MediaType.parse("image/*"), fileKeja))
        builder.addFormDataPart("foto_file", fileFoto.getName(), RequestBody.create(MediaType.parse("image/*"), fileFoto))
        builder.addFormDataPart("nik_file", fileNik.getName(), RequestBody.create(MediaType.parse("image/*"), fileNik))
        builder.addFormDataPart("foto_slip_gaji",fileGaji.getName(), RequestBody.create(MediaType.parse("image/*"), fileGaji))
        builder.addFormDataPart("foto_pegang_idcard", fileSelfi.getName(), RequestBody.create(MediaType.parse("image/*"), fileSelfi))
        val requestBody:RequestBody = builder.build()
        mPresenter.sendUpload(requestBody)
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

    fun easyImage(){
        EasyImage.openChooserWithGallery(this,"Pilih untuk cari gambar",1)
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
                            llFoto.visibility = View.GONE
                            imgFoto.setOnClickListener {
                                imgfoto = null
                                llFoto.visibility = View.VISIBLE
                                imgFoto.visibility = View.GONE
                            }
                            Glide.with(imgFoto).load(imagesFiles[i]).into(imgFoto)
                        }
                        1->{
                            imgNIK = imagesFiles.get(i)
                            imgKTP.visibility = View.VISIBLE
                            llNIK.visibility = View.GONE
                            imgKTP.setOnClickListener {
                                imgNIK = null
                                llNIK.visibility = View.VISIBLE
                                imgKTP.visibility = View.GONE
                            }
                            Glide.with(imgKTP).load(imagesFiles[i]).into(imgKTP)
                        }
                        2->{
                            imgkerja = imagesFiles.get(i)
                            imgSuratKerja.visibility = View.VISIBLE
                            Glide.with(imgSuratKerja).load(imagesFiles[i]).into(imgSuratKerja)
                            llKerja.visibility = View.GONE
                            imgSuratKerja.setOnClickListener {
                                imgkerja = null
                                llKerja.visibility = View.VISIBLE
                                imgSuratKerja.visibility = View.GONE
                            }
                        }
                        3->{
                            imggaji = imagesFiles.get(i)
                            imgSlipGaji.visibility = View.VISIBLE
                            Glide.with(imgSlipGaji).load(imagesFiles[i]).into(imgSlipGaji)
                            llGaji.visibility = View.GONE
                            imgSlipGaji.setOnClickListener {
                                imggaji = null
                                llGaji.visibility = View.VISIBLE
                                imgSlipGaji.visibility = View.GONE
                            }
                        }
                        4->{
                            imgselfi = imagesFiles.get(i)
                            imgSelfiKtp.visibility = View.VISIBLE
                            Glide.with(imgSelfiKtp).load(imagesFiles[i]).into(imgSelfiKtp)
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

        Log.d("berak","${content}")
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

}
