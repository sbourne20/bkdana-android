package rzgonz.bkd.modules.peminjam.custome

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import kotlinx.android.synthetic.main.popup_proses_pembiayaan.*
import rzgonz.bkd.R

open  class DialogProsesPembiayaan(context: Context) : Dialog(context,R.style.WideDialog),View.OnClickListener{



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        setContentView(R.layout.popup_proses_pembiayaan)
        btnSubmit.setOnClickListener(this)
    }



    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.btnSubmit ->{
                dismiss()
            }
        }


    }
}