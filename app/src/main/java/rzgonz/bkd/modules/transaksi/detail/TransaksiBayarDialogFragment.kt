package rzgonz.bkd.modules.transaksi.detail


import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.view.Gravity
import android.view.WindowManager
import android.graphics.Point
import android.text.Html
import android.widget.Toast
import kotlinx.android.synthetic.main.popup_proses_pembayaran.*
import rzgonz.bkd.R


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
class TransaksiBayarDialogFragment : DialogFragment(){
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    interface Listener{
        fun sendBayar()
    }


    var listener: Listener?= null

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
        fun newInstance(saldo:String,tagihan:String) =
                TransaksiBayarDialogFragment().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, saldo)
                        putString(ARG_PARAM2, tagihan)
                    }
                }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.popup_proses_pembayaran, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        tvSaldo.setText("SALDO : ${arguments?.getString(ARG_PARAM1)}")
        tvTagihan.setText("TAGIHAN : ${arguments?.getString(ARG_PARAM2)}")
        tvInfo.setText(Html.fromHtml(tvInfo.text.toString()))

        val saldo = arguments?.getString(ARG_PARAM1)?.toDouble()!!
        val tagihan = arguments?.getString(ARG_PARAM2)?.toDouble()!!
        btnSubmit.setOnClickListener {
                if(saldo >= tagihan){
                    dismiss()
                    listener?.sendBayar()
                }else{
                    Toast.makeText(it.context,"Saldo Tidak Cukup",Toast.LENGTH_LONG).show()
                }

        }


    }

    override fun onResume() {
        super.onResume()
        val window = dialog.window
        val size = Point()
        val display = window!!.windowManager.defaultDisplay
        display.getSize(size)
        window.setLayout((size.x * 0.90).toInt(), WindowManager.LayoutParams.WRAP_CONTENT)
        window.setGravity(Gravity.CENTER)
    }
}

