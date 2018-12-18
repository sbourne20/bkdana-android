package rzgonz.bkd.modules.transaksi


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.animation.OvershootInterpolator
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter
import jp.wasabeef.recyclerview.animators.SlideInLeftAnimator
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator
import kotlinx.android.synthetic.main.fragment_transaksi.*
import rzgonz.bkd.Apps.APKModel
import rzgonz.bkd.R
import rzgonz.bkd.injector.transaksi.DaggerTransaksiComponent
import rzgonz.bkd.models.transaksi.TransaksiResponse
import rzgonz.bkd.modules.transaksi.adapter.TransaksiAdapter
import rzgonz.core.kotlin.adapter.BaseRVAdapter
import rzgonz.core.kotlin.fragment.DIBaseFragment
import rzgonz.core.kotlin.view.CustomeRV
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [TransaksiFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class TransaksiFragment : DIBaseFragment(),CustomeRV.RVListener,TransaksiContract.View {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment TransaksiFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                TransaksiFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    @Inject
    lateinit var transaksiPresenter: TransaksiPresenter

    override fun inject() {
        DaggerTransaksiComponent.builder().appsComponent(APKModel.appsComponent).build().inject(this)
    }

    override fun onAttachView() {
        transaksiPresenter.attachView(this)
    }

    override fun onDetachView() {
        transaksiPresenter.detachView()

    }

    override fun initLayout(): Int {
       return R.layout.fragment_transaksi
    }

    override fun initUI(savedInstanceState: Bundle?) {
        rvView.listener(this)
        transaksiPresenter.getListTransaksi()
        rvView.getRv().itemAnimator = SlideInUpAnimator().apply {
            setInterpolator(OvershootInterpolator())
        }
    }

    override fun initAdapter(): BaseRVAdapter {
        return TransaksiAdapter(activity!!.baseContext, ArrayList<Any>())
    }

    override fun onLoadItems(limit: Int, offset: Int) {
//        var data = ArrayList<String>()
//        for (i in 0..10) {
//            data.add("${rvView.getAdapter().colomCount + i}")
//        }
//        return rvView.getAdapter().setItems(data)

    }

    override fun initRV(): CustomeRV {
        return rvView
    }

    override fun returnTransaksi(status: Boolean, responde: TransaksiResponse?, message: String) {
        if(status){
                rvView.getAdapter().setItems(responde?.listTransaksi!!)
        }
    }
}

