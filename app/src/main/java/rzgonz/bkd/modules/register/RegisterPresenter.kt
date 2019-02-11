package rzgonz.bkd.modules.register

import android.content.Context
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rzgonz.bkd.models.BaseResponse
import rzgonz.bkd.services.APIService
import rzgonz.core.kotlin.helper.APIHelper
import rzgonz.core.kotlin.presenter.DIBasePresenter
import javax.inject.Inject

class RegisterPresenter @Inject constructor(context: Context) : DIBasePresenter<RegisterContract.View>(),RegisterContract.Presenter{

    val apiService = APIHelper.getClient().create(APIService::class.java)

    override fun sendRegister(fullname: String, telp: String, email: String, pass: String, repass: String, sumberdana: String) {
        apiService.sendReg(fullname,email,telp,pass,repass,sumberdana).enqueue(object : Callback<BaseResponse<String>>{
            override fun onFailure(call: Call<BaseResponse<String>>, t: Throwable) {
                getView()?.returnRegister(false,null,"Gagal register harap coba kembali")
            }

            override fun onResponse(call: Call<BaseResponse<String>>, response: Response<BaseResponse<String>>) {
                if(response.isSuccessful){
                    if(!response.body()?.response.equals("fail")) {
                        getView()?.returnRegister(true, response.body(), "success")
                    }else{
                        getView()?.returnRegister(false,null,response.body()?.message!!)
                    }
                }else{
                    getView()?.returnRegister(false,null,response.body()?.message!!)
                }
            }
        })
    }

    override fun sendRegisterPinjam(fullname: String, telp: String, email: String, pass: String, repass: String, type: Int) {
        apiService.sendRegPinjam(fullname,email,telp,pass,repass,type).enqueue(object : Callback<BaseResponse<String>>{
            override fun onFailure(call: Call<BaseResponse<String>>, t: Throwable) {
                getView()?.returnRegister(false,null,"Gagal register harap coba kembali")
            }

            override fun onResponse(call: Call<BaseResponse<String>>, response: Response<BaseResponse<String>>) {
                if(response.isSuccessful){
                    if(!response.body()?.response.equals("fail")) {
                        getView()?.returnRegister(true, response.body(), "success")
                    }else{
                        getView()?.returnRegister(false,null,response.body()?.message!!)
                    }
                }else{
                    getView()?.returnRegister(false,null,response.body()?.message!!)
                }
            }
        })
    }

    override fun sendPhone(phone: String) {
        apiService.postPhone("+62${phone}").enqueue(object :Callback<BaseResponse<String>>{
            override fun onFailure(call: Call<BaseResponse<String>>, t: Throwable) {
                getView()?.returnPhone(false,null,"Gagal register harap coba kembali")

            }

            override fun onResponse(call: Call<BaseResponse<String>>, response: Response<BaseResponse<String>>) {
                if(response.isSuccessful){
                    if(response.body()?.response?.contains("fail")!!) {
                        getView()?.returnPhone(true, "bisa dipake", "success")
                    }else{
                        getView()?.returnPhone(false,null,response.body()?.message!!)
                    }
                }else{
                    getView()?.returnPhone(false,null,response.body()?.message!!)
                }
            }
        })
    }
}