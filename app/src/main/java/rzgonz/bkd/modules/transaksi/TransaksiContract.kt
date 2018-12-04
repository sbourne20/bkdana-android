package rzgonz.bkd.modules.transaksi

import rzgonz.bkd.models.transaksi.TransaksiResponse
import rzgonz.core.kotlin.contract.DIBaseContract
/**
 * Created by rzgonz on 7/10/17.
 */

object TransaksiContract  {

    interface View : DIBaseContract.View {
            fun returnTransaksi(status : Boolean, responde: TransaksiResponse?, message:String)
    }


    interface Presenter : DIBaseContract.Presenter<View> {

        fun getListTransaksi()
    }
}