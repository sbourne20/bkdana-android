package rzgonz.bkd.modules.transaksi.dana

import rzgonz.bkd.models.transaksi.dana.detail.TransaksiDanaDetailResponse
import rzgonz.bkd.models.transaksi.detail.DetailTransaksiResponse
import rzgonz.core.kotlin.contract.DIBaseContract
/**
 * Created by rzgonz on 7/10/17.
 */

object DetailTransaksiDanaContract  {

    interface View : DIBaseContract.View {
            fun returnDetialTransaksi(status : Boolean, responde: TransaksiDanaDetailResponse?, message:String)

    }


    interface Presenter : DIBaseContract.Presenter<View> {

        fun getDetail(id:String)
    }
}