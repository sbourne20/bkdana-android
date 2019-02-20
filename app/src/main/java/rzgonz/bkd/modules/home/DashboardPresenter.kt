package rzgonz.bkd.modules.home

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rzgonz.bkd.models.BaseResponse
import rzgonz.bkd.models.dashboard.MySaldoResponse
import rzgonz.bkd.models.user.UserResponse

import rzgonz.bkd.services.PinjamanService
import rzgonz.core.kotlin.helper.APIHelper
import rzgonz.core.kotlin.presenter.DIBasePresenter

class DashboardPresenter () : DIBasePresenter<DashboardContract.View>(),DashboardContract.Presenter{

    val apiService = APIHelper.getClient().create(PinjamanService::class.java)

    override fun getMyData() {

        apiService.getMyData().enqueue(object : Callback<UserResponse> {
            override fun onFailure(call: Call<UserResponse>?, t: Throwable?) {
                getView()?.returnUser(false,null,"${t}")
            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if(response.isSuccessful){
                    getView()?.returnUser(true,response.body()?.content,"success")
                }else{
                    getView()?.returnUser(false,null,"${response.errorBody()}")
                }
            }
        })
    }

    override fun getMySaldo() {
        apiService.getMysaldo().enqueue(object : Callback<MySaldoResponse> {
            override fun onFailure(call: Call<MySaldoResponse>?, t: Throwable?) {
                getView()?.returnMySaldo(false,null,"${t}")
            }

            override fun onResponse(call: Call<MySaldoResponse>, response: Response<MySaldoResponse>) {
                if(response.isSuccessful){
                    getView()?.returnMySaldo(true,response.body(),"success")
                }else{
                    getView()?.returnMySaldo(false,null,"${response.errorBody()}")
                }
            }
        })
    }

}