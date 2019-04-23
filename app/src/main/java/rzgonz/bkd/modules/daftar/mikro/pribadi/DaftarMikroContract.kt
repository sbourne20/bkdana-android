package rzgonz.bkd.modules.daftar.mikro.pribadi

import rzgonz.bkd.models.provinsi.ProvinsiResponse
import rzgonz.bkd.models.user.UserContent
import rzgonz.core.kotlin.contract.DIBaseContract

object DaftarMikroContract  {

    interface View : DIBaseContract.View {
        fun returnUser(status : Boolean, responde: UserContent?, message:String)
        fun returnSendUser(status : Boolean, responde: String?, message:String)
        fun returnProvinsi(status: Boolean, responde: ProvinsiResponse?, message: String)
        fun returnFcmtoken(status : Boolean, responde: UserContent?, message:String)
    }


    interface Presenter : DIBaseContract.Presenter<View> {

        fun getMyData()
        fun sendMyData(user:UserContent)
        fun getProvinsi()
        fun getfcmToken(fcmtoken: String)
    }
}