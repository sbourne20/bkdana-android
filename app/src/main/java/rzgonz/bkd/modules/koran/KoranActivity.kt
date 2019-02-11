package rzgonz.bkd.modules.koran

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_koran.*
import rzgonz.bkd.R
import rzgonz.bkd.models.koran.KoranListResponse
import rzgonz.bkd.modules.koran.adapter.KoranAdapter
import rzgonz.core.kotlin.activity.DIBaseActivity
import rzgonz.core.kotlin.adapter.BaseRVAdapter
import rzgonz.core.kotlin.view.CustomeRV

class KoranActivity :  DIBaseActivity(), CustomeRV.RVListener,KoranContract.View {

    val mPresenter = KoranPresenter()

    override fun inject() {

    }

    override fun onAttachView() {
        mPresenter.attachView(this)
    }

    override fun onDetachView() {
        mPresenter.detachView()
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
        mPresenter.getKoranList()
    }

    override fun initRV(): CustomeRV {
        return rvView
    }

    override fun returnKoranList(status: Boolean, responde: KoranListResponse?, message: String) {
        if(status){
            rvView.getAdapter().setItems(responde?.content!!)
        }else{
            if(rvView.getAdapter().getItems().size == 0) vf.displayedChild = 1

            showMessage(message)
        }
    }
}
