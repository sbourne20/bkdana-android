package rzgonz.bkd.modules.daftar.mikro.pribadi

import rzgonz.bkd.models.LoginResponse
import rzgonz.bkd.models.user.Content
import rzgonz.bkd.models.user.UserResponse
import rzgonz.core.kotlin.contract.DIBaseContract

object DaftarMikroContract  {

    interface View : DIBaseContract.View {
        fun returnUser(status : Boolean, responde: Content?, message:String)
        fun returnSendUser(status : Boolean, responde: String?, message:String)
    }


    interface Presenter : DIBaseContract.Presenter<View> {

        fun getMyData()
        fun sendMyData(user:Content)
    }
}