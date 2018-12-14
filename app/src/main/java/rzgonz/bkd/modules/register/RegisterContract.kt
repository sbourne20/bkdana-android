package rzgonz.bkd.modules.register

import rzgonz.bkd.models.BaseResponse
import rzgonz.core.kotlin.contract.DIBaseContract

/**
 * Created by rzgonz on 7/10/17.
 */

object RegisterContract  {

    interface View : DIBaseContract.View {
            fun returnRegister(status : Boolean,responde:BaseResponse<String>?,message:String)
    }


    interface Presenter : DIBaseContract.Presenter<View> {

        fun sendRegister(fullname:String,telp:String,email:String,pass:String,repass:String,sumberdana:String)
        fun sendRegisterMikro(fullname:String,telp:String,email:String,pass:String,repass:String)
        fun sendRegisterKilat(fullname:String,telp:String,email:String,pass:String,repass:String)
    }
}