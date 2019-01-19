package rzgonz.bkd.modules.daftar.mikro.usaha

import okhttp3.RequestBody
import rzgonz.bkd.models.LoginResponse
import rzgonz.bkd.models.user.Content
import rzgonz.bkd.models.user.UserResponse
import rzgonz.core.kotlin.contract.DIBaseContract

object DaftarMikroUsahaContract  {

    interface View : DIBaseContract.View {
        fun returnDataUsaha(status : Boolean, responde: String?, message:String)
    }


    interface Presenter : DIBaseContract.Presenter<View> {
        fun sendDataUsaha(dataUsaha:RequestBody)
    }
}