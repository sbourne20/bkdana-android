package rzgonz.bkd.modules.peminjam.detial

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detail_pinjaman.*
import rzgonz.bkd.Apps.APKModel
import rzgonz.bkd.R
import rzgonz.bkd.injector.transaksi.DaggerTransaksiComponent
import rzgonz.bkd.models.transaksi.ListTransaksiItem
import rzgonz.bkd.models.transaksi.detail.DetailTransaksiResponse
import rzgonz.bkd.modules.peminjam.custome.DialogProsesPembiayaan
import rzgonz.bkd.modules.transaksi.adapter.TransaksiAdapter
import rzgonz.core.kotlin.activity.DIBaseActivity
import javax.inject.Inject

class DetailPinjamanActivity : DIBaseActivity() {

//    @Inject
//    lateinit var detialtransaksiPresenter: DetailTransaksiPresenter

    override fun inject() {
      //  DaggerTransaksiComponent.builder().appsComponent(APKModel.appsComponent).build().inject(this)
    }

    override fun onAttachView() {
      //  detialtransaksiPresenter.attachView(this)
    }

    override fun onDetachView() {
     //   detialtransaksiPresenter.detachView()
    }

    override fun initLayout(): Int {
       return R.layout.activity_detail_pinjaman
    }

    override fun initUI(savedInstanceState: Bundle?) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Detail Transaksi")
        supportActionBar?.setSubtitle("Detail Transaksi Anda")
        val detail = intent.getParcelableExtra<ListTransaksiItem>(TransaksiAdapter.DETIAL)
      //  detialtransaksiPresenter.getDetail(detail.transaksiId!!)
        btnBayar.setOnClickListener {
            DialogProsesPembiayaan(this).show()
        }
    }

//    override fun returnDetialTransaksi(status: Boolean, responde: DetailTransaksiResponse?, message: String) {
//        if(status){
//            tvNoTransaksi.setText(responde?.content?.transaksi?.transaksiId)
//            tvNoTransaksiSub.setText(responde?.content?.transaksi?.masterLoanId)
//            tvNamaPeminjam.setText(responde?.content?.transaksi?.namaPeminjam)
//        }
//    }
}
