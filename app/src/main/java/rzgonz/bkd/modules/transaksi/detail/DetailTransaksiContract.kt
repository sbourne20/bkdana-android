package rzgonz.bkd.modules.transaksi.detail

import rzgonz.bkd.models.transaksi.detail.DetailTransaksiResponse
import rzgonz.core.kotlin.contract.DIBaseContract
/**
 * Created by rzgonz on 7/10/17.
 */

object DetailTransaksiContract  {

    interface View : DIBaseContract.View {
            fun returnDetialTransaksi(status : Boolean, responde: DetailTransaksiResponse?, message:String)
            fun retrunAngsuran(status : Boolean, responde: String?, message:String)
    }


    interface Presenter : DIBaseContract.Presenter<View> {

        fun getDetail(id:String)
        fun postAngsuran(id:String,nominal_pendanaan:String)
    }
}