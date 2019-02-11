package rzgonz.bkd.modules.topup


import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import kotlinx.android.synthetic.main.fragment_topup.*
import rzgonz.bkd.Apps.APKModel
import rzgonz.bkd.R
import rzgonz.bkd.injector.topup.DaggerTopupComponent
import rzgonz.bkd.models.bank.BankItem
import rzgonz.bkd.models.topup.ContentItem
import rzgonz.bkd.modules.midtrans.MidtransActivity
import rzgonz.bkd.modules.topup.adapter.TopupAdapter
import rzgonz.core.kotlin.fragment.DIBaseFragment
import javax.inject.Inject
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.ThreadMode
import org.greenrobot.eventbus.Subscribe
import kotlinx.android.synthetic.main.fragment_dashboard.*
import rzgonz.bkd.constant.BKD
import rzgonz.bkd.models.BaseResponse
import rzgonz.bkd.models.user.UserContent
import rzgonz.core.kotlin.helper.SharedPreferenceService


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
class TopupFragment : DIBaseFragment(),TopupContract.View,TopupDialogFragment.TopupSubmit {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    var progressDialog: ProgressDialog? = null

    val mAdapter = TopupAdapter()
    var loading  = false
    var PAGE = 1
    val LIMIT = 10

    val listBank = ArrayList<BankItem>()

    @Inject
    lateinit var mPresenter: TopupPresenter

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
                TopupFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                    }
                }
    }

    override fun inject() {
        DaggerTopupComponent.builder().appsComponent(APKModel.appsComponent).build().inject(this)
    }

    override fun onAttachView() {
        mPresenter.attachView(this)
    }

    override fun onDetachView() {
        mPresenter.detachView()
    }

    override fun initLayout(): Int {
       return R.layout.fragment_topup
    }

    override fun initUI(savedInstanceState: Bundle?) {
        toolbar?.setTitle("Top Up")
        toolbar?.setSubtitle("Kanal Pembayaran")
        val mLayoutManager = LinearLayoutManager(context)
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL)
        rvView.setLayoutManager(mLayoutManager)

        vf.displayedChild = 0
        // Disabled nested scrolling since Parent scrollview will scroll the content.
        rvView.setNestedScrollingEnabled(false)

        rvView.setAdapter(mAdapter)

        btnTopup.setOnClickListener {
            showTopup()
        }

        rvView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val totalItemCount = mAdapter.getItemCount()
                val lastVisibleItem = (rvView.layoutManager as LinearLayoutManager).findLastCompletelyVisibleItemPosition()
                if (!loading && totalItemCount <= (lastVisibleItem + 4)) {
                    loading = true
                    mPresenter.getListTopup(LIMIT,PAGE)
                }
            }
        })
        mPresenter.getListTopup(LIMIT,PAGE)
        mPresenter.getListBank()
    }

      fun showTopup() {
      val fm = activity?.getSupportFragmentManager();
      val editNameDialogFragment = TopupDialogFragment.newInstance(listBank)
          editNameDialogFragment.listener = this
      editNameDialogFragment.show(fm, "fragment_edit_name")
  }

    override fun retrunListBank(status: Boolean, responde: List<BankItem?>?, message: String) {
        if(status){
            responde?.forEach {
                listBank.add(it!!)
            }
        }else{
            showMessage(message)
        }
    }

    override fun retrunListTopup(status: Boolean, responde: List<ContentItem?>?, message: String) {
        vf.displayedChild = 1
        if(status){
            if(responde?.size!! > 0){
                PAGE++
                mAdapter.addAll(responde)
            }else{
                showMessage(message)
            }
        }else{
            if(rvView.adapter?.itemCount == 0 ) vf.displayedChild = 2

//            showMessage(message)
        }
    }

    override fun sendTopUp(nama: String, noRek: String, bank: String, bankTujuan: String, jumlah: String) {
        showProgressDialog(activity,"Mohon Tunggu",false)
        mPresenter.postTopup(nama,noRek,bank,jumlah)

    }

    override fun returnPostTopup(status: Boolean, responde: BaseResponse<String>?, message: String) {
        progressDialog?.dismiss()
      if(status){
          startActivity(Intent(activity,MidtransActivity::class.java).putExtra("url",responde?.content))
      }else{
          showMessage(message)
      }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: UserContent) {/* Do something */
        tvName.setText(event.namaPengguna)
        val type = SharedPreferenceService(context).getInt(BKD.LOGINTYPE,0)

        when(type){
            1->tvAccountType.setText("Peminjam")
                2->tvAccountType.setText("Pendana")
        }

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

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

}

