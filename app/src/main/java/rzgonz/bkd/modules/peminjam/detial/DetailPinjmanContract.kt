package rzgonz.bkd.modules.peminjam.detial

import rzgonz.bkd.models.peminjam.ListPeminjamItem
import rzgonz.bkd.models.peminjam.detail.DetailPinjam
import rzgonz.core.kotlin.contract.DIBaseContract
/**
 * Created by rzgonz on 7/10/17.
 */

object DetailPinjmanContract  {

    interface View : DIBaseContract.View {
            fun returnDetial(status : Boolean, responde: DetailPinjam?, message:String)
            fun retrunPendanaan(status : Boolean, responde:String?, message:String?)
            fun ceckStatus(status: Boolean,responde: String?,message: String?)
    }


    interface Presenter : DIBaseContract.Presenter<View> {

        fun getDetail(id:String)
        fun postPendanaan(id:String,nominal_pendanaan:String)
        fun checkbiayai()
    }
}