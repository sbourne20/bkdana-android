package rzgonz.bkd.modules.daftar.mikro

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import com.bumptech.glide.Glide
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_daftar_usaha_mirko.*
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import rzgonz.bkd.R
import rzgonz.core.kotlin.activity.DIBaseActivity
import java.io.File

class DaftarUsahaMirkoActivity : DIBaseActivity() {

    var pathfoto : ArrayList<File> = ArrayList()

    override fun inject() {

    }

    override fun onAttachView() {

    }

    override fun onDetachView() {

    }

    override fun initLayout(): Int {
      return R.layout.activity_daftar_usaha_mirko
    }

    override fun initUI(savedInstanceState: Bundle?) {
        collapsing_toolbar.isTitleEnabled = false
        collapsing_toolbar.title = "Daftat BKDana Mikro"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Daftar BKDana Mikro")
        btnSelanjutnya.setOnClickListener {
            finish()
            //startActivity(Intent(baseContext,DaftarUploadMikroActivity::class.java))
        }

        btnInfoUsaha.setOnClickListener {
            easyImage()
        }
    }

    fun easyImage(){
        EasyImage.configuration(this).setAllowMultiplePickInGallery(true)
        EasyImage.openChooserWithGallery(this,"Pilih untuk cari gambar",1)
    }

    override fun onDestroy() {
        super.onDestroy()
        EasyImage.clearConfiguration(this)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        EasyImage.handleActivityResult(requestCode, resultCode, data, this, object : DefaultCallback() {

            override fun onImagePickerError(e: Exception?, source: EasyImage.ImageSource?, type: Int) {
                //Some error handling
            }

            override fun onImagesPicked(imagesFiles: List<File>, source: EasyImage.ImageSource, type: Int) {
                //Handle the images
                for (i in 0..imagesFiles.size) {
                    val imageView = ImageView(baseContext)
                    //setting image position
                    var layout = LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT,
                            LinearLayout.LayoutParams.MATCH_PARENT
                    )
                    layout.setMargins(10,10,10,10)
                    imageView.setLayoutParams(layout)
                    imageView.setOnClickListener {
                        it.visibility= View.GONE
                        pathfoto.remove(imagesFiles[i])
                    }
                    pathfoto.add(imagesFiles[i])
                    Glide.with(imageView).load(imagesFiles[i]).into(imageView)
                    llUsaha.addView(imageView)
                }
            }
        })
    }
}
