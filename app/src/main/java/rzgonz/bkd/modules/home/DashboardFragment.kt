package rzgonz.bkd.modules.home


import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_dashboard.*

import rzgonz.bkd.R
import rzgonz.core.kotlin.view.CustomeViewPager

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DashboardFragment.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class DashboardFragment : Fragment(),CustomeViewPager.PagerListener {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var handler : Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cvpBanner.listener = this
        cvpBanner.setAdapter(activity)
        indicator.setViewPager(cvpBanner)
        runBanner()
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment DashboardFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                DashboardFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

    override fun initViewPager(): Int {
    return R.id.cvpBanner
    }

    override fun initViewTabLayout() {

    }

    override fun addFragment(): ArrayList<Fragment> {
        val items: ArrayList<Fragment> = ArrayList()
        items.add(BannerFragement.newInstance(1,"3"))
        items.add(BannerFragement.newInstance(2,"4"))
        return items
    }

     fun runBanner() {
        handler= Handler()
         handler.postDelayed({
             if (cvpBanner.adapter?.count!! > 0) {
             if (cvpBanner.currentItem < cvpBanner!!.adapter!!.count - 1) {
                 moveBannerPage(cvpBanner.getCurrentItem() + 1)
             } else {
                 moveBannerPage(0)
             }
         }}
         ,5000)

    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeMessages(0)
    }

    private fun moveBannerPage(page: Int) {
        cvpBanner.setCurrentItem(page, true)
        indicator.setViewPager(cvpBanner)
        runBanner()
    }
}
