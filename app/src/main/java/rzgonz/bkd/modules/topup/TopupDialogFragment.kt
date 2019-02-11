package rzgonz.bkd.modules.topup


import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.view.Gravity
import android.view.WindowManager
import android.graphics.Point
import kotlinx.android.synthetic.main.fragment_topup_submit.*
import rzgonz.bkd.R
import rzgonz.bkd.models.bank.BankItem
import rzgonz.bkd.modules.topup.adapter.BankAdapter


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
class TopupDialogFragment : DialogFragment(){
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null


    interface TopupSubmit{
        fun sendTopUp(nama:String,noRek:String,bank:String,bankTujuan:String,jumlah:String)
    }


    var listener:TopupSubmit?= null

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
        fun newInstance(param1: ArrayList<BankItem>) =
                TopupDialogFragment().apply {
                    arguments = Bundle().apply {
                        putParcelableArrayList(ARG_PARAM1, param1)
                    }
                }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_topup_submit, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
       val spinnerAdapter = BankAdapter(spBank.context,arguments?.getParcelableArrayList(ARG_PARAM1)!!)
        spBank.adapter = spinnerAdapter

        btnSelanjutnya.setOnClickListener {
            if(checkInput()) {
                dismiss()
                listener?.sendTopUp(etNamaRek.text.toString(), etNoRek.text.toString(), spinnerAdapter.getItem(spBank.selectedItemPosition)?.bankName!!, "", etJumlah.text.toString())
            }
        }
    }

    private fun checkInput(): Boolean {
        if(etNamaRek.text.isNullOrEmpty()){
            etNamaRek.setError( "is required!" )
            return  false
        }
        if(etNoRek.text.isNullOrEmpty()){
            etNoRek.setError( "is required!" )
            return  false
        }

        if(etJumlah.text.isNullOrEmpty()){
            etJumlah.setError( "is required!" )
            return  false
        }

        return true
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

