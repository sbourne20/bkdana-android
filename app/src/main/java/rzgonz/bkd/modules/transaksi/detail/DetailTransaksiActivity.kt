package rzgonz.bkd.modules.transaksi.detail

import android.os.Bundle
import android.os.Handler
import android.support.v7.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_detail_transaksi.*
import rzgonz.bkd.Apps.APKModel
import rzgonz.bkd.R
import rzgonz.bkd.injector.transaksi.DaggerTransaksiComponent
import rzgonz.bkd.models.transaksi.ListTransaksiItem
import rzgonz.bkd.models.transaksi.detail.DetailTransaksiResponse
import rzgonz.bkd.models.transaksi.detail.LogPinjaman
import rzgonz.bkd.modules.transaksi.adapter.TransaksiDetailAdapter
import rzgonz.bkd.modules.transaksi.adapter.TransaksiAdapter
import rzgonz.core.kotlin.activity.DIBaseActivity
import javax.inject.Inject

class DetailTransaksiActivity : DIBaseActivity(),DetailTransaksiContract.View {

    @Inject
    lateinit var detialtransaksiPresenter: DetailTransaksiPresenter

    override fun inject() {
        DaggerTransaksiComponent.builder().appsComponent(APKModel.appsComponent).build().inject(this)
    }

    override fun onAttachView() {
        detialtransaksiPresenter.attachView(this)
    }

    override fun onDetachView() {
        detialtransaksiPresenter.detachView()
    }

    override fun initLayout(): Int {
       return R.layout.activity_detail_transaksi
    }

    override fun initUI(savedInstanceState: Bundle?) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Detail Transaksi")
        supportActionBar?.setSubtitle("Detail Transaksi Anda")
        val detail = intent.getParcelableExtra<ListTransaksiItem>(TransaksiAdapter.DETIAL)
        detialtransaksiPresenter.getDetail(detail.transaksiId!!)



        // use a linear layout manager
        val mLayoutManager = LinearLayoutManager(baseContext)
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL)
        rvView2.setLayoutManager(mLayoutManager)

        // Disabled nested scrolling since Parent scrollview will scroll the content.
        rvView2.setNestedScrollingEnabled(false)

        // specify an adapter (see also next example)

        Handler().postDelayed({

        }, 5000)
    }

    override fun returnDetialTransaksi(status: Boolean, responde: DetailTransaksiResponse?, message: String) {
        if(status){
            tvNoTransaksi.setText(responde?.content?.transaksi?.transaksiId)
            tvNoTransaksiSub.setText(responde?.content?.transaksi?.masterLoanId)
            tvNamaPeminjam.setText(responde?.content?.transaksi?.namaPeminjam)
            tvTenor.setText("${responde?.content?.transaksi?.loanTerm!!} Bulan")
            tvJumDana.setText("${responde?.content.transaksi.totalLender}")
            tvJumPijam.setText("${responde?.content.transaksi.totalLoanOutstanding} IDR")
            tvTotal.setText("${responde?.content.transaksi.totalLoanRepayment} IDR")
            tvStatus.setText("${responde?.content.transaksi.masterLoanStatus}")

            val data = ArrayList<LogPinjaman>()
            for (index in 0..responde.content.logPinjaman?.ltpLamaAngsuran!!.toInt()){
                data.add(responde.content.logPinjaman)
            }
            val adapter2 =  TransaksiDetailAdapter(data)
            rvView2.setAdapter(adapter2)

        }
    }

}
