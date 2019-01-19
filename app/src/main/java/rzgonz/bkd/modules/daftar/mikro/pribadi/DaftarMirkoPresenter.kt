package rzgonz.bkd.modules.daftar.mikro.pribadi

import android.content.Context
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rzgonz.bkd.models.BaseResponse
import rzgonz.bkd.models.user.Content
import rzgonz.bkd.models.user.UserResponse
import rzgonz.bkd.services.APIService
import rzgonz.bkd.services.PinjamanService
import rzgonz.core.kotlin.helper.APIHelper
import rzgonz.core.kotlin.presenter.DIBasePresenter
import javax.inject.Inject

class DaftarMirkoPresenter @Inject constructor(context: Context) : DIBasePresenter<DaftarMikroContract.View>(),DaftarMikroContract.Presenter{

    val apiService = APIHelper.getClient().create(PinjamanService::class.java)

    override fun getMyData() {

        apiService.getMyData().enqueue(object : Callback<UserResponse> {
            override fun onFailure(call: Call<UserResponse>?, t: Throwable?) {
                getView()?.returnUser(false,null,"Username or Password Salah")
            }

            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if(response.isSuccessful){
                    getView()?.returnUser(true,response.body()?.content,"success")
                }else{
                    getView()?.returnUser(false,null,"no data")
                }
            }
        })
    }

    override fun sendMyData(user: Content) {

        apiService.sendMirko1(user.tempatLahir!!,user.jenisKelamin!!,user.alamat!!,user.kota!!,user.provinsi!!, user.kodepos!!)
                .enqueue(object : Callback<BaseResponse<String>> {
            override fun onFailure(call: Call<BaseResponse<String>>?, t: Throwable?) {
                getView()?.returnSendUser(false,null,"Username or Password Salah")
            }

            override fun onResponse(call: Call<BaseResponse<String>>, response: Response<BaseResponse<String>>) {
                if(response.isSuccessful){
                    getView()?.returnSendUser(true,response.body()?.message,"success")
                }else{
                    getView()?.returnSendUser(false,null,"no data")
                }
            }
        })
    }
}