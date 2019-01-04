package rzgonz.bkd.modules.daftar.mikro

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_daftar_upload_mikro.*
import rzgonz.bkd.R
import rzgonz.bkd.modules.daftar.kilat.DaftarUploadKilatActivity
import rzgonz.core.kotlin.activity.DIBaseActivity

class DaftarUploadMikroActivity : DIBaseActivity() {

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
    }
}
