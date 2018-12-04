package rzgonz.bkd.modules.profile

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_pinjaman.*
import rzgonz.bkd.R
import rzgonz.bkd.modules.peminjam.adapter.PeminjamAdapter
import rzgonz.core.kotlin.activity.DIBaseActivity
import rzgonz.core.kotlin.adapter.BaseRVAdapter
import rzgonz.core.kotlin.view.CustomeRV

class EditProfileActivity : DIBaseActivity() {

    override fun inject() {

    }

    override fun onAttachView() {

    }

    override fun onDetachView() {

    }

    override fun initLayout(): Int {
        return R.layout.activity_edit_profile
    }

    override fun initUI(savedInstanceState: Bundle?) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Ubah Profil")
        supportActionBar?.setSubtitle("Isi Profil Anda Sesuai Dengan Identitas Anda")
    }
}
