package rzgonz.bkd.modules.redem


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_redem.*
import rzgonz.bkd.Apps.APKModel
import rzgonz.bkd.R
import rzgonz.bkd.injector.redem.DaggerRedemComponent
import rzgonz.bkd.models.bank.BankItem
import rzgonz.bkd.models.redem.ContentItem
import rzgonz.bkd.modules.topup.adapter.BankAdapter
import rzgonz.core.kotlin.fragment.DIBaseFragment
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
class RedemFragment : DIBaseFragment(),RedemContract.View {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    val mAdapter =  RedemAdapter()
    var loading  = false
    var PAGE = 1
    val LIMIT = 10

    val listBank = ArrayList<BankItem>()

    @Inject
    lateinit var mPresenter: RedemPresenter

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
        DaggerRedemComponent.builder().appsComponent(APKModel.appsComponent).build().inject(this)
    }

    override fun onAttachView() {
            mPresenter.attachView(this)
    }

    override fun onDetachView() {
            mPresenter.detachView()
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
        rvView.setAdapter(mAdapter)
        vf.displayedChild = 0


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
                 //   mPresenter.getListRedem(LIMIT,PAGE)
                }
            }
        })
        mPresenter.getListBank()

        btnSelanjutnya.setOnClickListener {
            if(chekInput()){
                mPresenter.sendRedem(listBank.get(spBank.selectedItemPosition).bankName!!,etNoRek.text.toString(),etJumlah.text.toString())
            }
        }
    }

    private fun chekInput(): Boolean {
        if(etNoRek.text.isNullOrEmpty()){
            etNoRek.setError( "is required!" )
            return  false
        }
        if(etJumlah.text.isNullOrEmpty()){
            etJumlah.setError( "is required!" )
            return  false
        }

        return true
    }

    override fun retrunListRedem(status: Boolean, responde: List<ContentItem?>?, message: String) {
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
            //showMessage(message)
        }
    }

    override fun retrunListBank(status: Boolean, responde: List<BankItem?>?, message: String) {
        if(status){
            responde?.forEach {
                listBank.add(it!!)
            }
            val spinnerAdapter = BankAdapter(spBank.context,listBank)
            spBank.adapter = spinnerAdapter
        }else{
            showMessage(message)
        }
    }

    override fun onResume() {
        super.onResume()
        mAdapter.clear()
        PAGE = 1
        mPresenter.getListRedem(LIMIT,PAGE)
    }

    override fun returnRedem(status: Boolean, responde: String?, message: String?) {
        showMessage(message!!)
        etJumlah.setText("")
        etNoRek.setText("")
    }
}

