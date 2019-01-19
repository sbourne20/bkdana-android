package rzgonz.bkd.modules.redem


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.animation.OvershootInterpolator
import kotlinx.android.synthetic.main.fragment_topup.*
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
class RedemFragment : DIBaseFragment() {
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
        fun newInstance(param1: String) =
                RedemFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                    }
                }
    }

    override fun inject() {

    }

    override fun onAttachView() {

    }

    override fun onDetachView() {


    }

    override fun initLayout(): Int {
       return R.layout.fragment_redem
    }

    override fun initUI(savedInstanceState: Bundle?) {
        toolbar?.setTitle("Redem")
        toolbar?.setSubtitle("Untuk Penebusan Saldo Anda")
        val mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL)
        rvView.setLayoutManager(mLayoutManager)

        // Disabled nested scrolling since Parent scrollview will scroll the content.
        rvView.setNestedScrollingEnabled(false)

        val data = ArrayList<String>()
        for (index in 0..100){
            data.add("no ${index}")
        }

        val adapter2 =  RedemAdapter(data)
        rvView.setAdapter(adapter2)
    }

}

