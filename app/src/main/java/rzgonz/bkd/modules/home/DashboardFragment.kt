package rzgonz.bkd.modules.home


import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.View
import kotlinx.android.synthetic.main.fragment_dashboard.*

import rzgonz.bkd.R
import rzgonz.core.kotlin.view.CustomeViewPager
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.ThreadMode
import org.greenrobot.eventbus.Subscribe
import rzgonz.bkd.models.Event
import rzgonz.bkd.models.dashboard.RepaymentResponse
import rzgonz.bkd.models.user.UserContent
import rzgonz.bkd.modules.daftar.kilat.pribadi.DaftarKilatActivity
import rzgonz.bkd.modules.daftar.mikro.pribadi.DaftarMikroActivity
import rzgonz.core.kotlin.fragment.DIBaseFragment


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
class DashboardFragment : DIBaseFragment(),CustomeViewPager.PagerListener,DashboardContract.DashboardView {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var handler : Handler

    val mPresenter = DashboardFragmentPresenter()

    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }



    override fun initLayout(): Int {
        return R.layout.fragment_dashboard
    }

    override fun initUI(savedInstanceState: Bundle?) {
        cvpBanner.listener = this
        cvpBanner.setAdapter(activity)
        cvpBanner.clipToPadding = false
        cvpBanner.setPadding(0, 0, 40, 0)
        indicator.setViewPager(cvpBanner)
        runBanner()
        mPresenter.getMyRepayment()
        llOne.visibility =View.GONE
        llTwo.visibility =View.GONE
        btnRefresh.visibility =View.GONE
//        btnRefresh.setOnClickListener {
//            mPresenter.getMyRepayment()
//        }

        sr.setOnRefreshListener {
            mPresenter.getMyRepayment()
        }

        tvName.setText(arguments?.getString(ARG_PARAM1,""))
    }

    override fun inject() {

    }



    override fun onAttachView() {
        mPresenter.attachView(this)
    }

    override fun onDetachView() {
        mPresenter.detachView()
    }

    override fun returnMyRepayment(status: Boolean, responde: RepaymentResponse?, message: String) {
        progressDialog?.dismiss()
        sr.isRefreshing = false
        if(status){
            tvTotal.setText("${responde?.content?.saldo}")
            tvTolalPendaanan.setText("Jumlah Pendanaan : ${responde?.content?.jmlAllTransaksi}")
            tvAccountType.setText(responde?.content?.tipeUser)
            responde?.content?.listRepayment?.forEachIndexed { index, listRepaymentItem ->
                when(index){
                    0 ->{
                        llOne.visibility = View.VISIBLE
                        tvTitleOne.setText(listRepaymentItem?.titleTransaksi)
                        tvTvTempoOne.setText("Jatuh Tempo : ${listRepaymentItem?.jatuhTempoTransaksi}")
                        tvJumlahOne.setText(listRepaymentItem?.nominalTransaksi)
                    }
                    1 ->{
                        llTwo.visibility = View.VISIBLE
                        tvTItleTwo.setText(listRepaymentItem?.titleTransaksi)
                        tvTempoTwo.setText("Jatuh Tempo : ${listRepaymentItem?.jatuhTempoTransaksi}")
                        tvJUmlahTwo.setText(listRepaymentItem?.nominalTransaksi)
                    }
                }
            }
        }else{
            showMessage(message)
        }
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

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: UserContent) {
      //  Glide.with(imgFoto).load(event)
        tvName.setText(event.namaPengguna)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onBannerEvet(event: Event) {
        showProgressDialog(activity,"Mohon Tunggu",false)
        mPresenter.checkPinjaman(event.value)
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

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }



    fun showProgressDialog(context: Context?, message: String?, isCancelable: Boolean) {
        if (context == null) return
        // TODO: Change Progress dialog color
        progressDialog = ProgressDialog(context)
        progressDialog?.run {
            if (message != null) {
                progressDialog!!.setMessage(message)
            }
            setIndeterminate(true)
            setCancelable(isCancelable)
            setCanceledOnTouchOutside(false)
            show()
        }
    }

    override fun returnCheckPinjaman(status: Boolean, responde: String?, message: String?) {
        progressDialog?.dismiss()
        if(true){
            if(responde.equals("1")){
                startActivity(Intent(this.context, DaftarKilatActivity::class.java))
            }else if(responde.equals("2")){
                startActivity(Intent(this.context, DaftarMikroActivity::class.java))
            }

        }else{
            showError(message)
        }
    }

}
