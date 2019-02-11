package rzgonz.bkd.modules.peminjam


import rzgonz.bkd.models.bank.BankItem
import rzgonz.bkd.models.peminjam.ListPeminjamItem
import rzgonz.bkd.models.peminjam.PeminjamContent
import rzgonz.bkd.models.redem.ContentItem
import rzgonz.core.kotlin.contract.DIBaseContract
/**
 * Created by rzgonz on 7/10/17.
 */

object PeminjamContract  {

    interface View : DIBaseContract.View {
            fun retrunListPeminjam(status : Boolean, responde: List<ListPeminjamItem?>?, message:String)

    }


    interface Presenter : DIBaseContract.Presenter<View> {

        fun getListPeminjam(limit:Int,page:Int)
    }
}