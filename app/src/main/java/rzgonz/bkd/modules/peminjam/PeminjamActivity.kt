package rzgonz.bkd.modules.peminjam

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_pinjaman.*
import rzgonz.bkd.R
import rzgonz.bkd.modules.peminjam.adapter.PeminjamAdapter
import rzgonz.core.kotlin.activity.DIBaseActivity
import rzgonz.core.kotlin.adapter.BaseRVAdapter
import rzgonz.core.kotlin.view.CustomeRV

class PeminjamActivity : DIBaseActivity(),CustomeRV.RVListener {

    override fun inject() {

    }

    override fun onAttachView() {

    }

    override fun onDetachView() {

    }

    override fun initLayout(): Int {
        return R.layout.activity_pinjaman
    }

    override fun initUI(savedInstanceState: Bundle?) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Daftar Peminjam")
        supportActionBar?.setSubtitle("Daftar Peminjam Yang Sedang Aktif")
        rvView.listener(this)
    }

    override fun initAdapter(): BaseRVAdapter {
        return PeminjamAdapter(this, ArrayList<Any>())
    }

    override fun onLoadItems(limit: Int, offset: Int) {
        var data= ArrayList<String>()
        for (i in 0..10){
            data.add("${rvView.getAdapter().colomCount+i}")
        }
        return rvView.getAdapter().setItems(data)
    }

    override fun initRV(): CustomeRV {
        return rvView
    }
}
