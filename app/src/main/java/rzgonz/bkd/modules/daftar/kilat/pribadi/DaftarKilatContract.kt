package rzgonz.bkd.modules.daftar.kilat.pribadi

import rzgonz.bkd.models.provinsi.ProvinsiResponse
import rzgonz.bkd.models.user.UserContent
import rzgonz.core.kotlin.contract.DIBaseContract

object DaftarKilatContract  {

    interface View : DIBaseContract.View {
        fun returnUser(status : Boolean, responde: UserContent?, message:String)
        fun returnSendUser(status : Boolean, responde: String?, message:String)
        fun returnProvinsi(status: Boolean, responde: ProvinsiResponse?, message: String)
    }


    interface Presenter : DIBaseContract.Presenter<View> {
        fun getMyData()
        fun sendMyData(user:UserContent)
        fun getProvinsi()
    }
}