package rzgonz.bkd.modules.topup

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

class TopupPresenter @Inject constructor(val context: Context) : DIBasePresenter<TopupContract.View>(),TopupContract.Presenter{

    val apiService = APIHelper.getClient().create(APIService::class.java)

    override fun getListTopup(limit: Int, page: Int) {
        apiService.getTopupList(limit,page).enqueue(object :Callback<ListTopupResponse>{
            override fun onFailure(call: Call<ListTopupResponse>, t: Throwable) {
                getView()?.retrunListTopup(false,null,"${t}")
            }

            override fun onResponse(call: Call<ListTopupResponse>, response: Response<ListTopupResponse>) {
                if(response.isSuccessful){
                    if(response.body()?.response.equals("success"))
                        getView()?.retrunListTopup(true,response.body()?.content,"success")
                    else
                        getView()?.retrunListTopup(false,null,"Tidak Ada data")
                }else{
                    getView()?.retrunListTopup(false,null,"Tidak Ada data")
                }
            }
        })
    }

    override fun getListBank() {
        apiService.getListBank().enqueue(object :Callback<ListBankResponse>{
            override fun onFailure(call: Call<ListBankResponse>, t: Throwable) {
                getView()?.retrunListBank(false,null,"${t}")
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

    override fun postTopup(nama_rekening: String, nomor_rekening: String, nama_bank: String, jumlah_topup: String) {
        apiService.postTopup(nama_rekening,nomor_rekening,nama_bank,jumlah_topup).enqueue(object :Callback<BaseResponse<String>>{
            override fun onFailure(call: Call<BaseResponse<String>>, t: Throwable) {
                getView()?.returnPostTopup(false,null,"${t}")
            }

            override fun onResponse(call: Call<BaseResponse<String>>, response: Response<BaseResponse<String>>) {
                if(response.isSuccessful){
                    if(response.body()?.response.equals("success"))
                        getView()?.returnPostTopup(true,response.body(),"success")
                    else
                        getView()?.returnPostTopup(false,null,"Tidak Ada data")
                }else{
                    getView()?.retrunListTopup(false,null,"Tidak Ada data")
                }
            }
        })
    }
}