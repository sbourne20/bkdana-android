package rzgonz.bkd.modules.koran

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_pinjaman.*
import rzgonz.bkd.R
import rzgonz.bkd.modules.koran.adapter.KoranAdapter
import rzgonz.bkd.modules.peminjam.adapter.PeminjamAdapter
import rzgonz.core.kotlin.activity.DIBaseActivity
import rzgonz.core.kotlin.adapter.BaseRVAdapter
import rzgonz.core.kotlin.view.CustomeRV

class KoranActivity :  DIBaseActivity(), CustomeRV.RVListener {

    override fun inject() {

    }

    override fun onAttachView() {

    }

    override fun onDetachView() {

    }

    override fun initLayout(): Int {
        return R.layout.activity_koran
    }

    override fun initUI(savedInstanceState: Bundle?) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Rekening Koran")
        rvView.listener(this)
    }

    override fun initAdapter(): BaseRVAdapter {
        return KoranAdapter(this, ArrayList<Any>())
    }

    override fun onLoadItems(limit: Int, offset: Int) {
        var data = ArrayList<String>()
        for (i in 0..10) {
            data.add("${rvView.getAdapter().colomCount + i}")
        }
        return rvView.getAdapter().setItems(data)
    }

    override fun initRV(): CustomeRV {
        return rvView
    }
}
