package rzgonz.bkd.modules.Login

import rzgonz.core.kotlin.contract.DIBaseContract
/**
 * Created by rzgonz on 7/10/17.
 */

object LoginContract  {

    interface View : DIBaseContract.View {
            fun returnLogin(status : Boolean,responde:String,message:String)
    }


    interface Presenter : DIBaseContract.Presenter<View> {

        fun checkLogin()
    }
}