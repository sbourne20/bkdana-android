package rzgonz.bkd.modules.daftar.kilat

import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_daftar_upload_kilat.*
import rzgonz.bkd.R
import rzgonz.core.kotlin.activity.DIBaseActivity

class DaftarUploadKilatActivity : DIBaseActivity() {


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

    }

    override fun inject() {

    }
    override fun onAttachView() {

    }

    override fun onDetachView() {

    }

}
