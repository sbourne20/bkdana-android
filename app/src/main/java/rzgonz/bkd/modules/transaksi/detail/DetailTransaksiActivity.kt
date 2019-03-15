package rzgonz.bkd.modules.transaksi.detail

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import kotlinx.android.synthetic.main.activity_detail_transaksi.*
import rzgonz.bkd.Apps.APKModel
import rzgonz.bkd.Apps.toThousand
import rzgonz.bkd.R
import rzgonz.bkd.injector.transaksi.DaggerTransaksiComponent
import rzgonz.bkd.models.transaksi.ListTransaksiItem
import rzgonz.bkd.models.transaksi.detail.DetailTransaksiResponse
import rzgonz.bkd.models.transaksi.detail.LogPinjaman
import rzgonz.bkd.modules.password.ConfimPasswordActivity
import rzgonz.bkd.modules.transaksi.adapter.TransaksiDetailAdapter
import rzgonz.bkd.modules.transaksi.adapter.TransaksiAdapter
import rzgonz.core.kotlin.activity.DIBaseActivity
import rzgonz.core.kotlin.helper.SharedPreferenceService
import javax.inject.Inject

class DetailTransaksiActivity : DIBaseActivity(),DetailTransaksiContract.View,TransaksiBayarDialogFragment.Listener {

    @Inject
    lateinit var mPresenter: DetailTransaksiPresenter

    override fun inject() {
        DaggerTransaksiComponent.builder().appsComponent(APKModel.appsComponent).build().inject(this)
    }

    override fun onAttachView() {
        mPresenter.attachView(this)
    }

    override fun onDetachView() {
        mPresenter.detachView()
    }

    override fun initLayout(): Int {
       return R.layout.activity_detail_transaksi
    }

    override fun initUI(savedInstanceState: Bundle?) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Detail Transaksi")
        supportActionBar?.setSubtitle("Detail Transaksi Anda")
        btnBayar.visibility = View.GONE

        // use a linear layout manager
        val mLayoutManager = LinearLayoutManager(baseContext)
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL)
        rvView2.setLayoutManager(mLayoutManager)

        // Disabled nested scrolling since Parent scrollview will scroll the content.
        rvView2.setNestedScrollingEnabled(false)

        // specify an adapter (see also next example)
    }

    fun showTopup(tagihan:String) {
        val saldo =  SharedPreferenceService(applicationContext).getString("SALDO","0")
        val fm = getSupportFragmentManager();
        val editNameDialogFragment = TransaksiBayarDialogFragment.newInstance(saldo,tagihan)
        editNameDialogFragment.listener = this
        editNameDialogFragment.show(fm, "fragment_edit_name")
    }


    override fun returnDetialTransaksi(status: Boolean, responde: DetailTransaksiResponse?, message: String) {
        progressDialog?.dismiss()
        if(status){
            tvNoTransaksi.setText(responde?.content?.transaksi?.transaksiId)
            tvNamaPeminjam.setText(responde?.content?.transaksi?.namaPeminjam)
            tvTenor.setText("${responde?.content?.transaksi?.loanTerm!!}")
            tvNamaTransaksi.setText("${responde?.content?.transaksi?.typeBusinessName} ")
            tvJumDana.setText("${responde?.content.transaksi.jmlPermohonanPinjamanDisetujui?.toInt()?.toThousand()} IDR")
            tvJumPijam.setText("${responde?.content.transaksi.jmlPermohonanPinjaman?.toInt()?.toThousand()} IDR")
            tvStatusDana.setText("${responde?.content.transaksi.masterLoanStatus}")
            tvJatuhTempo.setText("${responde?.content.jatuhTempo}")
            val adapter2 =  TransaksiDetailAdapter(responde.content.repaymentList)
            rvView2.setAdapter(adapter2)

            if(tvStatusDana.text.equals("complete") or tvStatusDana.text.equals("didanai")){
                btnBayar.visibility = View.VISIBLE
            }else{
                btnBayar.visibility = View.GONE
            }
            btnBayar.hint =responde?.content?.nominalJmlAngsuran!!
            btnBayar.setOnClickListener {
                startActivityForResult(Intent(this, ConfimPasswordActivity::class.java),99)

            }

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 99){
            if (resultCode == Activity.RESULT_OK) {
                showTopup(btnBayar.hint.toString())
            }
        }
    }


override fun sendBayar() {
        showProgressDialog(this,"Mohon Tunggu",false)
        mPresenter.postAngsuran(tvNoTransaksi.text.toString(),btnBayar.hint.toString())
    }

    override fun retrunAngsuran(status: Boolean, responde: String?, message: String) {
        progressDialog?.dismiss()
        if(status) {
            finish()
        }
        showMessage(message)
    }

    override fun onResume() {
        super.onResume()
        showProgressDialog(this,"Mohon Tunggu",false)
        val detail = intent.getParcelableExtra<ListTransaksiItem>(TransaksiAdapter.DETIAL)
        mPresenter.getDetail(detail.transaksiId!!)
    }

}
