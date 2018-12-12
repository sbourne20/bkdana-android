package rzgonz.bkd.modules.splashscreen

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_worktour.*

import rzgonz.bkd.R

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [WorktourFragemnt.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [WorktourFragemnt.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class WorktourFragemnt : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_worktour, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnSkip.visibility =View.VISIBLE
        when(arguments?.getString(ARG_PARAM1)){
             "1" ->{
                 imgIcon.setImageResource(R.drawable.icon_why_1)
                 tvTitle.setText("Akses di multi perangkat")
                 tvSubTitle.setText("BKDana hadir di multi platform, baik di Web, Android dan iOS, memudahkan transaksi dimanapun dan kapanpun.")
                }
            "2"->{
                tvTitle.setText("Risiko Terukur & Transparan")
                tvSubTitle.setText("BKDana hanya menyalurkan kredit ke pemijam yang tervirifikasi dan dilengkapi analisa credit scoring melalui jejak digital terpercaya.")
              //  imgIcon.setImageResource(R.drawable.icon_why_2)

            }
            "3" ->{
                btnSkip.visibility =View.VISIBLE
                tvTitle.setText("Penggunaan Mudah & Aman")
                tvSubTitle.setText("Platform BKDana sangat mudah digunakan dan diawasi oleh OJK")
               // imgIcon.setImageResource(R.drawable.icon_why_3)
            }
        }

        btnSkip.setOnClickListener {
            startActivity(Intent(activity,HomeActivity::class.java))
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WorktourFragemnt.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                WorktourFragemnt().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
