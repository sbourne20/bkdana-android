package rzgonz.bkd.modules.peminjam
import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_pinjaman.*
import rzgonz.bkd.R
import rzgonz.bkd.models.peminjam.ListPeminjamItem
import rzgonz.bkd.modules.home.DashboardActivity
import rzgonz.bkd.modules.peminjam.adapter.PeminjamAdapter
import rzgonz.core.kotlin.activity.DIBaseActivity
import rzgonz.core.kotlin.adapter.BaseRVAdapter
import rzgonz.core.kotlin.view.CustomeRV

class PeminjamActivity : DIBaseActivity(),CustomeRV.RVListener,PeminjamContract.View {

    val mPresenter = PeminjamPresenter()
    var PAGE= 0
    override fun inject() {

    }

    override fun onAttachView() {
        mPresenter.attachView(this)
    }

    override fun onDetachView() {
        mPresenter.detachView()
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

    override fun onRestart() {
        super.onRestart()
        rvView.onRefresh()
    }


    override fun onLoadItems(limit: Int, offset: Int) {
//        var data= ArrayList<String>()
//        for (i in 0..10){
//            data.add("${rvView.getAdapter().colomCount+i}")
//        }
//        return rvView.getAdapter().setItems(data)
        if(offset==0){
            PAGE=0
        }

        mPresenter.getListPeminjam(limit,PAGE)
    }

    override fun retrunListPeminjam(status: Boolean, responde: List<ListPeminjamItem?>?, message: String) {
        if(status){
            PAGE++
            rvView.getAdapter().setItems(responde!!)
        }else{
            if (rvView.getAdapter().itemCount ==0){
                rvView.errorLoading()
                showMessage(message)
            }
        }

    }

    override fun initRV(): CustomeRV {
        return rvView
    }
}
