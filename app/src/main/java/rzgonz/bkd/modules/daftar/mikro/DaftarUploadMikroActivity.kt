package rzgonz.bkd.modules.daftar.mikro

import android.Manifest
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_daftar_upload_mikro.*
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import rzgonz.bkd.R
import rzgonz.bkd.modules.daftar.kilat.DaftarUploadKilatActivity
import rzgonz.core.kotlin.activity.DIBaseActivity
import java.io.File

class DaftarUploadMikroActivity : DIBaseActivity() {

    var imgfoto = -1
    var imgnik = -2
    var imgusaha = -3


    var pathfoto : File? = null
    var pathnik : File? = null
    var pathusaha : File? = null

    var flagImage :Int = 0

    override fun inject() {

    }

    override fun onAttachView() {

    }

    override fun onDetachView() {

    }

    override fun initLayout(): Int {
        return R.layout.activity_daftar_upload_mikro
    }

    override fun initUI(savedInstanceState: Bundle?) {
        collapsing_toolbar.isTitleEnabled = false
        collapsing_toolbar.title = "Daftat BKDana Mikro"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Daftar BKDana Mikro")
        btnSelanjutnya.setOnClickListener {
            startActivity(Intent(baseContext, DaftarUsahaMirkoActivity::class.java))
        }

        btnFoto.setOnClickListener {
            flagImage = imgfoto
            easyImage()
        }

        btnNIK.setOnClickListener {
            flagImage = imgnik
            easyImage()
        }
        btnUsaha.setOnClickListener {
            flagImage = imgusaha
            easyImage()
        }

    }

    override fun onResume() {
        super.onResume()
        checkPermissionAndCreateCamera()
    }

    fun easyImage(){
        EasyImage.openChooserWithGallery(this,"Pilih untuk cari gambar",1)
    }

    fun easyDoc(){
        EasyImage.openChooserWithGallery(this,"Pilih untuk cari Document",1)
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
                    when (flagImage) {
                        imgfoto -> {
                            pathfoto = imagesFiles.get(i)
                            imgFoto.visibility = View.VISIBLE
                            llFoto.visibility = View.INVISIBLE
                            Glide.with(imgFoto).load(imagesFiles[i]).into(imgFoto)
                            imgFoto.setOnClickListener {
                                imgFoto.visibility = View.INVISIBLE
                                pathfoto = null
                                llFoto.visibility = View.VISIBLE
                            }
                        }

                        imgnik -> {
                            pathnik = imagesFiles.get(i)
                            imgKTP.visibility = View.VISIBLE
                            Glide.with(imgKTP).load(imagesFiles[i]).into(imgKTP)
                            llNIK.visibility = View.INVISIBLE
                            imgKTP.setOnClickListener {
                                imgKTP.visibility = View.INVISIBLE
                                pathnik = null
                                llNIK.visibility = View.VISIBLE
                            }
                        }

                        imgusaha -> {
                            pathusaha = imagesFiles.get(i)
                            imgUsaha.visibility = View.VISIBLE
                            Glide.with(imgUsaha).load(imagesFiles[i]).into(imgUsaha)
                            llUsaha.visibility = View.INVISIBLE
                            imgUsaha.setOnClickListener {
                                imgUsaha.visibility = View.INVISIBLE
                                pathusaha = null
                                llUsaha.visibility = View.VISIBLE
                            }
                        }
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
