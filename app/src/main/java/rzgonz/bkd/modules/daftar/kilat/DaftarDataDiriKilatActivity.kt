package rzgonz.bkd.modules.daftar.kilat

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_daftar_data_diri_kilat.*
import kotlinx.android.synthetic.main.header_daftar.*
import rzgonz.bkd.R
import rzgonz.core.kotlin.activity.DIBaseActivity

class DaftarDataDiriKilatActivity : DIBaseActivity() {

    override fun initLayout(): Int {
        return R.layout.activity_daftar_data_diri_kilat
    }

    override fun initUI(savedInstanceState: Bundle?) {
        collapsing_toolbar.isTitleEnabled = false
        collapsing_toolbar.title = "Daftat BKDana Kilat"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Daftar BKDana Kilat")
        btnSelanjutnya.setOnClickListener {
            startActivity(Intent(baseContext,DaftarUploadKilatActivity::class.java))
        }

    }

    override fun inject() {

    }
    override fun onAttachView() {

    }

    override fun onDetachView() {

    }

}
