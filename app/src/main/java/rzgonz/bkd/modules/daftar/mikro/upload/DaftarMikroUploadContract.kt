package rzgonz.bkd.modules.daftar.mikro.upload

import okhttp3.RequestBody
import rzgonz.bkd.models.LoginResponse
import rzgonz.bkd.models.user.Content
import rzgonz.bkd.models.user.UserResponse
import rzgonz.core.kotlin.contract.DIBaseContract

object DaftarMikroUploadContract  {

    interface View : DIBaseContract.View {
        fun returnUpload(status : Boolean, responde: String?, message:String)
        fun retrunPijanam(status : Boolean, content: rzgonz.bkd.models.pinjaman.Content?, message:String)
    }


    interface Presenter : DIBaseContract.Presenter<View> {
        fun sendUpload(resquest:RequestBody)
        fun getPinjaman()
    }
}