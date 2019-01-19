package rzgonz.bkd.modules.daftar.kilat.upload

import okhttp3.RequestBody
import rzgonz.bkd.models.pinjaman.Content
import rzgonz.core.kotlin.contract.DIBaseContract

object DaftarKilatUploadContract  {

    interface View : DIBaseContract.View {
        fun retrunPijanam(status : Boolean, content:Content?, message:String)
        fun returnSendUpload(status: Boolean, responde: String?, message:String)
    }


    interface Presenter : DIBaseContract.Presenter<View> {

        fun getPinjaman()
        fun sendUpload(content: RequestBody)
    }
}