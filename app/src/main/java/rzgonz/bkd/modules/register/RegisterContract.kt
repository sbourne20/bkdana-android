package rzgonz.bkd.modules.register

import rzgonz.bkd.models.BaseResponse
import rzgonz.core.kotlin.contract.DIBaseContract

/**
 * Created by rzgonz on 7/10/17.
 */

object RegisterContract  {

    interface View : DIBaseContract.View {
            fun returnRegister(status : Boolean,responde:BaseResponse<String>?,message:String)
            fun returnPhone(status: Boolean,responde:String?,message: String)
    }


    interface Presenter : DIBaseContract.Presenter<View> {

        fun sendRegister(fullname:String,telp:String,email:String,pass:String,repass:String,sumberdana:String)
        fun sendPhone(phone:String)
        fun sendRegisterPinjam(fullname:String,telp:String,email:String,pass:String,repass:String,type:Int)
    }
}