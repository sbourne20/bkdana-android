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

    var imgfoto = -1
    var imgnik = -2
    var imgsuratkerja = -3
    var imgslipgaji = -4
    var imgselfiktp = -5

    var pathfoto :File? = null
    var pathnik :File? = null
    var pathsuratkerja :File? = null
    var pathslipgaji:File? = null
    var pathselfiktp:File? = null




    var flagImage :Int = 0

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
            flagImage = imgfoto
            easyImage()
        }

        btnNIK.setOnClickListener {
            flagImage = imgnik
            easyImage()
        }
        btnSuratKerja.setOnClickListener {
            flagImage = imgsuratkerja
            easyImage()
        }
        btnSlipGaji.setOnClickListener {
            flagImage = imgslipgaji
            easyImage()
        }
        btnSelfiKtp.setOnClickListener {
            flagImage = imgselfiktp
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
                    when(flagImage){
                        imgfoto ->{
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
                        imgnik ->{
                            pathnik = imagesFiles.get(i)
                            imgNIK.visibility = View.VISIBLE
                            Glide.with(imgNIK).load(imagesFiles[i]).into(imgNIK)
                            llNIK.visibility = View.INVISIBLE
                            imgNIK.setOnClickListener {
                                imgNIK.visibility = View.INVISIBLE
                                pathnik = null
                                llNIK.visibility = View.VISIBLE
                            }
                        }
                        imgsuratkerja ->{
                            pathsuratkerja = imagesFiles.get(i)
                            imgSuratKerja.visibility = View.VISIBLE
                            Glide.with(imgSuratKerja).load(imagesFiles[i]).into(imgSuratKerja)
                            llSuratKerja.visibility = View.INVISIBLE
                            imgSuratKerja.setOnClickListener {
                                imgSuratKerja.visibility = View.INVISIBLE
                                pathsuratkerja = null
                                llSuratKerja.visibility = View.VISIBLE
                            }
                        }
                        imgslipgaji ->{
                            pathslipgaji = imagesFiles.get(i)
                            imgSlipGaji.visibility = View.VISIBLE
                            Glide.with(imgSlipGaji).load(imagesFiles[i]).into(imgSlipGaji)
                            llSlipGaji.visibility = View.INVISIBLE
                            imgSlipGaji.setOnClickListener {
                                imgSlipGaji.visibility = View.INVISIBLE
                                pathslipgaji = null
                                llSlipGaji.visibility = View.VISIBLE
                            }
                        }
                        imgselfiktp ->{
                            pathselfiktp = imagesFiles.get(i)
                            imgSelfiKtp.visibility = View.VISIBLE
                            Glide.with(imgSelfiKtp).load(imagesFiles[i]).into(imgSelfiKtp)
                            llSelfi.visibility = View.INVISIBLE
                            imgSelfiKtp.setOnClickListener {
                                imgSelfiKtp.visibility = View.INVISIBLE
                                pathselfiktp = null
                                llSelfi.visibility = View.VISIBLE
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
