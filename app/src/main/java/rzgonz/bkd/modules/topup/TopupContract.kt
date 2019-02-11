package rzgonz.bkd.modules.topup


import rzgonz.bkd.models.BaseResponse
import rzgonz.bkd.models.bank.BankItem
import rzgonz.bkd.models.topup.ContentItem
import rzgonz.core.kotlin.contract.DIBaseContract
/**
 * Created by rzgonz on 7/10/17.
 */

object TopupContract  {

    interface View : DIBaseContract.View {
            fun retrunListTopup(status : Boolean, responde: List<ContentItem?>?, message:String)
            fun retrunListBank(status : Boolean, responde: List<BankItem?>?, message:String)
            fun returnPostTopup(status: Boolean,responde: BaseResponse<String>?,message:String)
    }


    interface Presenter : DIBaseContract.Presenter<View> {

        fun getListTopup(limit:Int,page:Int)
        fun getListBank()
        fun postTopup(nama_rekening:String,nomor_rekening:String,nama_bank:String,jumlah_topup:String)
    }
}