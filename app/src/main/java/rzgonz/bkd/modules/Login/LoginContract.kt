package rzgonz.bkd.modules.Login

import rzgonz.bkd.models.LoginResponse
import rzgonz.core.kotlin.contract.DIBaseContract
/**
 * Created by rzgonz on 7/10/17.
 */

object LoginContract  {

    interface View : DIBaseContract.View {
            fun returnLogin(status : Boolean,responde:LoginResponse?,message:String)
    }


    interface Presenter : DIBaseContract.Presenter<View> {

        fun checkLogin(username:String,password:String, fcmtoken:String)
        fun checkPassword(username:String,password:String)
    }
}