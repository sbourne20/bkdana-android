package rzgonz.bkd.modules.daftar.mikro

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_daftar_mikro.*
import rzgonz.bkd.R
import rzgonz.core.kotlin.activity.DIBaseActivity

class DaftarMikroActivity : DIBaseActivity() {

    override fun initLayout(): Int {
        return R.layout.activity_daftar_mikro
    }

    override fun initUI(savedInstanceState: Bundle?) {
        collapsing_toolbar.isTitleEnabled = false
        collapsing_toolbar.title = "Daftat BKDana Mikro"
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Daftar BKDana Mikro")
        btnSelanjutnya.setOnClickListener {
            startActivity(Intent(baseContext,DaftarUploadMikroActivity::class.java))
        }
    }

    override fun inject() {

    }
    override fun onAttachView() {

    }

    override fun onDetachView() {

    }
}
