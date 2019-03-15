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
import rzgonz.bkd.modules.password.ConfimPasswordActivity
import rzgonz.bkd.modules.peminjam.custome.DialogProsesPembiayaan
import rzgonz.core.kotlin.activity.DIBaseActivity
import android.app.Activity
import rzgonz.bkd.models.peminjam.detail.DetailPinjam


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

        setDetail(data)

    }

    override fun onResume() {
        super.onResume()
       // mPresenter.getDetail(data.transaksiId!!)
    }
    private fun setDetail(data: ListPeminjamItem?) {
        tvNoTransaksi.setText(data?.transaksiId)
        tvTotalpinjaman.setText("${data?.totalPinjam} IDR")
        tvTolalPendaanan.setText("${data?.jmlKredit} IDR")
        tvNamaPeminjam.setText(data?.namaPeminjam)
        tvLender.setText("${data?.totalLender} Lender mengikuti pendanaan ini")
        tvGrade.setText(data?.peringkatPengguna)
        tvTenor.setText(data?.productTitle)
        // progressBar.progress = data.totalPinjam!!.toDouble().times(100).div(data.totalApprove!!.toDouble()).roundToInt()
        tvProgress.setText("${data?.kuota_dana}")
        tvQuota.setText("Kuota ${data?.kuota_dana}")
        val  prog =  data?.kuota_dana?.replace("%","")?.toInt()
        if(prog !=null){
            progressBar.progress = (prog)
        }

        btnBayar.setOnClickListener {
            if(data?.kuota_dana?.contains("100",false)!!){
                showMessage("Maaf quota peminjaman sudah terpenuhi")
            }else{
                showProgressDialog(this,"Check Status",false)
               mPresenter.checkbiayai()
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == 99){
            if (resultCode == Activity.RESULT_OK) {
                DialogProsesPembiayaan(this,this.data).show()
            }

        }
    }

    override fun returnDetial(status: Boolean, responde: DetailPinjam?, message: String) {
        progressDialog?.dismiss()
        if(status){
           // setDetail(responde)
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

    override fun ceckStatus(status: Boolean, responde: String?, message: String?) {
        progressDialog?.dismiss()
        if(status){
            startActivityForResult(Intent(this,ConfimPasswordActivity::class.java),99)
        }else{
            showMessage("${message}")
        }
    }
}
