package rzgonz.bkd.modules.Login

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rzgonz.bkd.constant.BKD
import rzgonz.bkd.models.LoginResponse
import rzgonz.bkd.services.APIService

import rzgonz.bkd.services.interceptors.AuthTokenInterceptor
import rzgonz.core.kotlin.helper.APIHelper
import rzgonz.core.kotlin.helper.SharedPreferenceService
import rzgonz.core.kotlin.presenter.DIBasePresenter
import javax.inject.Inject

class LoginPresenter @Inject constructor(val context: Context) : DIBasePresenter<LoginContract.View>() ,LoginContract.Presenter {

    val apiService = APIHelper.getClient().create(APIService::class.java)

    override fun checkLogin(username: String, password: String, fcmtoken: String) {

        apiService.postLogin(username,password,fcmtoken).enqueue(object : Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
                getView()?.returnLogin(false,null,"error connection")
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if(response.isSuccessful){
                    val header = HashMap<String,String>()
                   header.set("Authorization",response.body()?.token!!)
                   // header.set("Authorization","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpZCI6IjIxNSIsImlzcyI6IjE0OS4xMjkuMjEzLjMwIiwiaWF0IjoxNTQ5MjI2NDEwLCJuYmYiOjE1NDkyMjY0MTAsImV4cCI6MTU1MDQzNjAxMCwibG9ndHlwZSI6IjIifQ.mlkkenrmMy6zz-qA-GDvv-XdFNKZLHTR7KIgVX0T6xQ20IOmAW8N1oGDHEKi8kWbsawUNYBcSRtBdrD_FG9VeA")
                    APIHelper.Headers  = header
                    APIHelper.setAuthInterceptor(AuthTokenInterceptor(context))
                    SharedPreferenceService(context).saveString(BKD.EMAIL,username)
                    SharedPreferenceService(context).saveString(BKD.TOKEN,response.body()?.token)
                    SharedPreferenceService(context).saveInt(BKD.LOGINTYPE,response.body()?.logtype!!.toInt())
                    getView()?.returnLogin(true,response.body(),"success")
                }else{
                    getView()?.returnLogin(false,null,"Username or Password salah")
                }
            }
        })
     }

    override fun checkPassword(username: String, password: String)  {
        apiService.postPassoword(username,password).enqueue(object : Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
                getView()?.returnLogin(false,null,"error connection")
            }
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if(response.isSuccessful){
                    getView()?.returnLogin(true,response.body(),"success")
                }else{
                    getView()?.returnLogin(false,null,"Username or Password salah")
                }
            }
        })
    }
}