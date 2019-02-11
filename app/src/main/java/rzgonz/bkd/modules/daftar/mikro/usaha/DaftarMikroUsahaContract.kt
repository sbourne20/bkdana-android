package rzgonz.bkd.modules.daftar.mikro.usaha

import okhttp3.RequestBody
import rzgonz.core.kotlin.contract.DIBaseContract

object DaftarMikroUsahaContract  {

    interface View : DIBaseContract.View {
        fun returnDataUsaha(status : Boolean, responde: String?, message:String)
    }


    interface Presenter : DIBaseContract.Presenter<View> {
        fun sendDataUsaha(dataUsaha:RequestBody)
    }
}