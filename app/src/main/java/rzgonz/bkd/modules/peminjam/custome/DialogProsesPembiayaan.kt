package rzgonz.bkd.modules.peminjam.custome

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Toast
import kotlinx.android.synthetic.main.popup_proses_pembiayaan.*
import org.greenrobot.eventbus.EventBus
import rzgonz.bkd.Apps.toThousand
import rzgonz.bkd.R
import rzgonz.bkd.models.peminjam.ListPeminjamItem
import rzgonz.bkd.models.peminjam.Message
import rzgonz.core.kotlin.helper.SharedPreferenceService

open  class DialogProsesPembiayaan(context: Context, val responde: ListPeminjamItem) : Dialog(context,R.style.WideDialog),View.OnClickListener{
    var tagihan = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.popup_proses_pembiayaan)
        tvSaldo.setText("Saldo : ${SharedPreferenceService(context).getString("SALDO","0")} IDR")
        tagihan = responde.totalPinjam?.replace(",","")?.toInt()!!.minus(responde.totalApprove?.replace(",","")?.toInt()!!)
        tvTagihan.setText("Tagihan : ${tagihan.toThousand()} IDR")
        btnSubmit.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btnSubmit ->{
                if(tagihan >= etPedanaan.text.toString().toInt()){
                EventBus.getDefault().post(Message(etPedanaan.text.toString()))
                dismiss()
                }else{
                    Toast.makeText(context,"Pendanaan Melebihi Tagihan",Toast.LENGTH_SHORT).show()
                }
            }
        }


    }
}