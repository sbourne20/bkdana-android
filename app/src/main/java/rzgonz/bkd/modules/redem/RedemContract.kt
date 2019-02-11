package rzgonz.bkd.modules.redem


import rzgonz.bkd.models.bank.BankItem
import rzgonz.bkd.models.redem.ContentItem
import rzgonz.core.kotlin.contract.DIBaseContract
/**
 * Created by rzgonz on 7/10/17.
 */

object RedemContract  {

    interface View : DIBaseContract.View {
            fun retrunListRedem(status : Boolean, responde: List<ContentItem?>?, message:String)
            fun retrunListBank(status : Boolean, responde: List<BankItem?>?, message:String)
            fun returnRedem(status: Boolean,responde:String?,message: String?)
    }


    interface Presenter : DIBaseContract.Presenter<View> {

        fun getListRedem(limit:Int,page:Int)
        fun getListBank()
        fun sendRedem(nama_bank:String,nomer_rek:String,amount:String)
    }
}