package rzgonz.bkd.modules.transaksi.dana

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_transaksi_dana_detail.*
import rzgonz.bkd.R
import rzgonz.bkd.models.transaksi.ListTransaksiItem
import rzgonz.bkd.models.transaksi.dana.detail.TransaksiDanaDetailResponse
import rzgonz.bkd.modules.transaksi.adapter.TransaksiAdapter
import rzgonz.core.kotlin.activity.DIBaseActivity

class TransaksiDanaDetailActivity : DIBaseActivity(),DetailTransaksiDanaContract.View {

     val mPresenter = DetailTransaksiDanaPresenter()

    override fun inject() {
    }

    override fun onAttachView() {
        mPresenter.attachView(this)
    }

    override fun onDetachView() {
        mPresenter.detachView()
    }
    override fun initLayout(): Int {
        return R.layout.activity_transaksi_dana_detail
    }

    override fun initUI(savedInstanceState: Bundle?) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Detail Transaksi")
        supportActionBar?.setSubtitle("Detail Transaksi Anda")
        val detail = intent.getParcelableExtra<ListTransaksiItem>(TransaksiAdapter.DETIAL)
        mPresenter.getDetail(detail.transaksiId!!)
        showProgressDialog(this,"Mohon Tunggu",false)

    }

    override fun returnDetialTransaksi(status: Boolean, responde: TransaksiDanaDetailResponse?, message: String) {
        progressDialog?.dismiss()
        if(status){
            setData(responde)
        }else{
            showMessage(message)
        }
    }

    private fun setData(data: TransaksiDanaDetailResponse?) {
        tvNoTransaksi.setText(data?.content?.noTransaksiPendanaan)
        tvNoTransaksiPemijam.setText(data?.content?.noTransaksiPinjaman)
        tvNamaPeminjam.setText(data?.content?.namaPeminjam)
        tvNamaTransaksi.setText(data?.content?.statusPinjaman)
        tvTenor.setText("${data?.content?.tenor}")
        tvJumDana.setText("${data?.content?.totalPendanaan}")
        tvJumPijam.setText("${data?.content?.totalPinjaman} IDR")
        tvStatusDana.setText("${data?.content?.statusPendanaan}")
        tvJatuhTempo.setText("${data?.content?.jatuhTempo}")
//
    }
}
