package rzgonz.bkd.modules.home

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rzgonz.bkd.models.checking.CheckPinjamanResponse
import rzgonz.bkd.models.dashboard.RepaymentResponse
import rzgonz.bkd.services.DashboardService
import rzgonz.core.kotlin.helper.APIHelper
import rzgonz.core.kotlin.presenter.DIBasePresenter

class DashboardFragmentPresenter : DIBasePresenter<DashboardContract.DashboardView>(),DashboardContract.DashboardPresenter{

    val apiService = APIHelper.getClient().create(DashboardService::class.java)

    override fun getMyRepayment() {
        apiService.getRepayment().enqueue(object :Callback<RepaymentResponse>{
            override fun onFailure(call: Call<RepaymentResponse>, t: Throwable) {
                getView()?.returnMyRepayment(false,null,"${t}")
            }

            override fun onResponse(call: Call<RepaymentResponse>, response: Response<RepaymentResponse>) {
                if(response.isSuccessful){
                    getView()?.returnMyRepayment(true,response.body(),"success")
                }else{
                    getView()?.returnMyRepayment(false,null,"${response.errorBody()}")
                }
            }
        })
    }
    override fun checkPinjaman(value: Int) {
        apiService.checkPinjaman().enqueue(object : Callback<CheckPinjamanResponse> {
            override fun onFailure(call: Call<CheckPinjamanResponse>?, t: Throwable?) {
                getView()?.returnCheckPinjaman(false,null,"${t}")
            }

            override fun onResponse(call: Call<CheckPinjamanResponse>, response: Response<CheckPinjamanResponse>) {
                if(response.isSuccessful){
                    if(response.body()?.response.equals("fail")){
                        getView()?.returnCheckPinjaman(true,value.toString(),response.body()?.message)
                    }else{
                        getView()?.returnCheckPinjaman(false,value.toString(),response.body()?.message)
                    }
                }else{
                    getView()?.returnCheckPinjaman(false,null,"${response.errorBody()}")
                }
            }
        })
    }
}