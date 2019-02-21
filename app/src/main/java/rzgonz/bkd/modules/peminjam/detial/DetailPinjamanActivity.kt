package rzgonz.bkd.modules.peminjam.detial

import android.content.Intent
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_detail_pinjaman.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import rzgonz.bkd.R
import rzgonz.bkd.models.peminjam.ListPeminjamItem
import rzgonz.bkd.models.peminjam.Message
import rzgonz.bkd.models.peminjam.detail.PeminjamDetailResponse
import rzgonz.bkd.modules.password.ConfimPasswordActivity
import rzgonz.bkd.modules.peminjam.custome.DialogProsesPembiayaan
import rzgonz.core.kotlin.activity.DIBaseActivity
import kotlin.math.roundToInt
import android.R.attr.data
import android.app.Activity


class DetailPinjamanActivity : DIBaseActivity(),DetailPinjmanContract.View {

    val  mPresenter = DetailPeminjamPresenter()
    lateinit var data : ListPeminjamItem
    override fun inject() {
      //  DaggerTransaksiComponent.builder().appsComponent(APKModel.appsComponent).build().inject(this)
    }

    override fun onAttachView() {
      mPresenter.attachView(this)
    }

    override fun onDetachView() {
        mPresenter.detachView()
    }

    override fun initLayout(): Int {
       return R.layout.activity_detail_pinjaman
    }

    override fun initUI(savedInstanceState: Bundle?) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle("Detail Transaksi")
        supportActionBar?.setSubtitle("Detail Transaksi Anda")
       // showProgressDialog(this,"Mohon tunggu",false)
        data = intent.getParcelableExtra("ID")

        tvQuota.setText("Kuota ${data.totalPinjam?.toDouble()?.times(100)?.div(data.totalApprove!!.toDouble())!!.roundToInt()}")
        tvNoTransaksi.setText(data.transaksiId)
        tvTotalpinjaman.setText(data.totalPinjam)
        tvTolalPendaanan.setText(data.totalApprove)
        tvNamaPeminjam.setText(data.namaPeminjam)
        tvLender.setText("${data.totalLender} Lender mengikuti pendanaan ini")
        tvGrade.setText(data.peringkatPengguna)
        tvTenor.setText(data.productTitle)
       // progressBar.progress = data.totalPinjam!!.toDouble().times(100).div(data.totalApprove!!.toDouble()).roundToInt()
        tvProgress.setText("${data.kuota_dana}")
        tvQuota.setText("Kuota ${data.kuota_dana}")
        val  prog =  data.kuota_dana?.replace("%","")?.toInt()
        if(prog !=null){
            progressBar.progress = (prog)
        }

        btnBayar.setOnClickListener {
            startActivityForResult(Intent(this,ConfimPasswordActivity::class.java),99)

        }
       // mPresenter.getDetail(intent.getStringExtra("ID"))

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 99){
            if (resultCode == Activity.RESULT_OK) {
                DialogProsesPembiayaan(this,this.data).show()
            }

        }
    }

    override fun returnDetial(status: Boolean, responde: PeminjamDetailResponse?, message: String) {
        progressDialog?.dismiss()
        if(status){
            tvNoTransaksi.setText(responde?.content?.noTransaksiPinjaman)
            tvTotalpinjaman.setText(responde?.content?.totalPinjaman)
            tvTolalPendaanan.setText(responde?.content?.totalPendanaan)
            tvNamaPeminjam.setText(responde?.content?.namaPeminjam)
            btnBayar.setOnClickListener {
              //  DialogProsesPembiayaan(this,responde).show()
            }

        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: Message) {
        showProgressDialog(this,"Mohon Tunggu",false)
        mPresenter.postPendanaan(tvNoTransaksi.text.toString(),event.nominal_pendanaan)

    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this);
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this);
    }

    override fun retrunPendanaan(status: Boolean, responde: String?, message: String?) {
        progressDialog?.dismiss()
        if(status){
            finish()
        }
        showMessage("${message}")
    }
}
