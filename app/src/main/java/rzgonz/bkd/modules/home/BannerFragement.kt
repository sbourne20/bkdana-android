package rzgonz.bkd.modules.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_banner_dashboard.*
import org.greenrobot.eventbus.EventBus

import rzgonz.bkd.R
import rzgonz.bkd.constant.BKD
import rzgonz.bkd.models.Event
import rzgonz.bkd.modules.daftar.kilat.pribadi.DaftarKilatActivity
import rzgonz.bkd.modules.daftar.mikro.pribadi.DaftarMikroActivity
import rzgonz.bkd.modules.peminjam.PeminjamActivity
import rzgonz.core.kotlin.fragment.DIBaseFragment
import rzgonz.core.kotlin.helper.SharedPreferenceService

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
class BannerFragement : DIBaseFragment() {
    // TODO: Rename and change types of parameters
    private var param1: Int? = null
    private var param2: String? = null

    override fun inject() {

    }

    override fun onAttachView() {

    }

    override fun onDetachView() {

    }

    override fun initLayout(): Int {
        return R.layout.fragment_banner_dashboard
    }

    override fun initUI(savedInstanceState: Bundle?) {
        arguments?.let {
            param1 = it.getInt(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
        when(arguments?.getInt(ARG_PARAM1)){
            1 ->{ cardBanner.background = resources.getDrawable(R.drawable.ic_card_red)
                imgBanner.setImageDrawable(resources.getDrawable(R.drawable.ic_banner_1))
                tvBannerTitle.setText("BKDana Kilat")
                tvBannerDetial.setText("Butuh dana Kilat 1 - 2 juta? Seperti biaya Rumah Sakit, Sekolah, Kontrakan, dll. Proses persetujuan hanya 15 menit!")
                cardBanner.setOnClickListener {
                    if(SharedPreferenceService(it.context).getInt(BKD.LOGINTYPE,0)==1) {
                        val data = Event(1)
                        EventBus.getDefault().post(data)
                    }else{
                        showMessage("Anda Login Sebagai Pendana")
                    }

                }
            }
            2 ->{cardBanner.background = resources.getDrawable(R.drawable.ic_card_aqua)
                imgBanner.setImageDrawable(resources.getDrawable(R.drawable.icon_register_2))
                tvBannerTitle.setText("BKDana Mikro")
                tvBannerDetial.setText("Pinjaman Mikro (Usaha Kecil) untuk solusi Bisnis anda. Platform maksimal sampai dengan 50 juta!")
                cardBanner.setOnClickListener {
                    if(SharedPreferenceService(it.context).getInt(BKD.LOGINTYPE,0)==1) {
                        val data  = Event(2)
                        EventBus.getDefault().post(data)
                    }else{
                        showMessage("Anda Login Sebagai Pendana")
                    }

                }
            }
            else -> { // Note the block
                print("x is neither 1 nor 2")
            }
        }
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
        fun newInstance(param1: Int, param2: String) =
                BannerFragement().apply {
                    arguments = Bundle().apply {
                        putInt(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
