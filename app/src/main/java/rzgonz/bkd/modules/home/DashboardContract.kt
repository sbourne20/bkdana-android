package rzgonz.bkd.modules.home

import rzgonz.bkd.models.dashboard.MySaldoResponse
import rzgonz.bkd.models.dashboard.RepaymentResponse
import rzgonz.bkd.models.user.UserContent
import rzgonz.core.kotlin.contract.DIBaseContract

object DashboardContract  {

    interface View : DIBaseContract.View {
        fun returnUser(status : Boolean, responde: UserContent?, message:String)
        fun returnMySaldo(status : Boolean, responde: MySaldoResponse?, message:String)
    }


    interface Presenter : DIBaseContract.Presenter<View> {

        fun getMyData()
        fun getMySaldo()
    }

    interface DashboardView : DIBaseContract.View {
        fun returnMyRepayment(status : Boolean, responde: RepaymentResponse?, message:String)
        fun returnCheckPinjaman(status : Boolean, responde: String?, message:String?)
    }


    interface DashboardPresenter : DIBaseContract.Presenter<DashboardView> {

        fun getMyRepayment()
        fun checkPinjaman(value: Int)
    }
}