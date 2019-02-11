package id.rzgonz.demokrat.base.custome

import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import rzgonz.bkd.R
import rzgonz.bkd.base.RvCallBack
import rzgonz.core.kotlin.holder.BaseItemHolder

class ViewLoading(v: View) : BaseItemHolder(v) {
    val pb : ProgressBar
    val tvLoadding  : TextView
    val btnTryAgain  : Button
    val llLoading  : LinearLayout
    lateinit var  callBack : RvCallBack
    init {
        pb = v.findViewById(R.id.pb)
        tvLoadding = v.findViewById(R.id.tvLoading)
        btnTryAgain = v.findViewById(R.id.btnTryAgain)
        llLoading = v.findViewById(R.id.llLoading)
    }

    fun initData(boolean: Boolean){
        if(boolean){
            pb.visibility = View.GONE
            tvLoadding.setText("No More Data")
        }else{
            pb.visibility = View.VISIBLE
            tvLoadding.setText("Loading...")
        }
    }


    fun setTryAgian(){
        Log.d("HISTORY","setTryAgain")
        llLoading.visibility = View.GONE
        btnTryAgain.visibility = View.VISIBLE
        btnTryAgain.setOnClickListener {
            it.visibility = View.GONE
            llLoading.visibility = View.VISIBLE
            pb.visibility = View.VISIBLE
            tvLoadding.setText("Loading...")
            callBack.onReaload()
        }
    }

    fun stopLoading(){
        llLoading.visibility = View.GONE
        btnTryAgain.visibility = View.GONE
    }

    fun setListener(listener : RvCallBack){
        callBack = listener
    }

}