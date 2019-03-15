package rzgonz.bkd.modules.redem

import android.content.Context
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rzgonz.bkd.models.BaseResponse
import rzgonz.bkd.models.bank.ListBankResponse
import rzgonz.bkd.models.redem.ListRedemResponse
import rzgonz.bkd.models.topup.ListTopupResponse
import rzgonz.bkd.services.APIService
import rzgonz.core.kotlin.helper.APIHelper
import rzgonz.core.kotlin.presenter.DIBasePresenter
import javax.inject.Inject

class RedemPresenter @Inject constructor(val context: Context) : DIBasePresenter<RedemContract.View>(),RedemContract.Presenter{

    val apiService = APIHelper.getClient().create(APIService::class.java)


    override fun getListRedem(limit: Int, page: Int) {
        apiService.getRedemList().enqueue(object :Callback<ListRedemResponse>{
            override fun onFailure(call: Call<ListRedemResponse>, t: Throwable) {
                getView()?.retrunListRedem(false,null,"error connection")
            }

            override fun onResponse(call: Call<ListRedemResponse>, response: Response<ListRedemResponse>) {
                if(response.isSuccessful){
                    if(response.body()?.response.equals("success"))
                        getView()?.retrunListRedem(true,response.body()?.content,"success")
                    else
                        getView()?.retrunListRedem(false,null,"Tidak Ada Data")
                }else{
                    getView()?.retrunListRedem(false,null,"Tidak Ada Data")
                }
            }
        })
    }

    override fun getListBank() {
        apiService.getListBank().enqueue(object :Callback<ListBankResponse>{
            override fun onFailure(call: Call<ListBankResponse>, t: Throwable) {
                getView()?.retrunListBank(false,null,"error connection")
            }

            override fun onResponse(call: Call<ListBankResponse>, response: Response<ListBankResponse>) {
                if(response.isSuccessful){
                    if(response.body()?.response.equals("success"))
                        getView()?.retrunListBank(true,response.body()?.content,"success")
                    else
                        getView()?.retrunListBank(false,null,"Tidak Ada data")
                }else{
                    getView()?.retrunListBank(false,null,"Tidak Ada data")
                }
            }
        })
    }

    override fun sendRedem(nama_bank: String, nomer_rek: String, amount: String) {
        apiService.postRedem(nomer_rek,nama_bank,amount).enqueue(object :Callback<BaseResponse<String>>{
            override fun onFailure(call: Call<BaseResponse<String>>, t: Throwable) {
                getView()?.returnRedem(false,null,"error connection")
            }

            override fun onResponse(call: Call<BaseResponse<String>>, response: Response<BaseResponse<String>>) {
                if(response.isSuccessful){
                    if(response.body()?.response.equals("success"))
                        getView()?.returnRedem(true,response.body()?.message,response.body()?.message)
                    else
                        getView()?.returnRedem(false,null,response.body()?.message)
                }else{
                    getView()?.returnRedem(false,null,"Tidak Ada data")
                }
            }
        })}
}