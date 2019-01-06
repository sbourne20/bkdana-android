package rzgonz.bkd.modules.daftar.kilat

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_daftar_upload_kilat.*
import kotlinx.android.synthetic.main.header_daftar.*
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import rzgonz.bkd.R
import rzgonz.core.kotlin.activity.DIBaseActivity
import java.io.File

class DaftarUploadKilatActivity : DIBaseActivity() {

    var isfoto = true
    var imgfoto : File? = null
    var imgNIK : File? = null

    override fun initLayout(): Int {
        return R.layout.activity_daftar_upload_kilat
    }

    override fun initUI(savedInstanceState: Bundle?) {
        collapsing_toolbar.isTitleEnabled = false
        collapsing_toolbar.title = "Daftat BKDana Kilat"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Daftar BKDana Kilat")
        btnSelanjutnya.setOnClickListener {
            finish()
        }

        btnFoto.setOnClickListener {
            isfoto = true
            easyImage()
        }

        btnNIK.setOnClickListener {
            isfoto = false
            easyImage()
        }
    }

    override fun inject() {

    }
    override fun onAttachView() {

    }

    override fun onDetachView() {

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
                    if (isfoto) {
                        imgfoto = imagesFiles.get(i)
                        imgFoto.visibility = View.VISIBLE
                        Glide.with(imgFoto).load(imagesFiles[i]).into(imgFoto)
                    } else {
                        imgNIK = imagesFiles.get(i)
                        imgKTP.visibility = View.VISIBLE
                        Glide.with(imgKTP).load(imagesFiles[i]).into(imgKTP)
                    }
                }
            }
        })
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
