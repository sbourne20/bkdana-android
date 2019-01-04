package rzgonz.bkd.modules.daftar.kilat

import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_daftar_kilat.*
import rzgonz.bkd.R
import rzgonz.core.kotlin.activity.DIBaseActivity

class DaftarKilatActivity : DIBaseActivity() {

    override fun initLayout(): Int {
        return R.layout.activity_daftar_kilat
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
