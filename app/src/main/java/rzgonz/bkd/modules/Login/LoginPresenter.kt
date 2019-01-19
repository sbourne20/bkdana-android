package rzgonz.bkd.modules.Login

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rzgonz.bkd.constant.BKD
import rzgonz.bkd.models.LoginResponse
import rzgonz.bkd.services.APIService
import rzgonz.core.kotlin.helper.APIHelper
import rzgonz.core.kotlin.helper.SharedPreferenceService
import rzgonz.core.kotlin.presenter.DIBasePresenter
import javax.inject.Inject

class LoginPresenter @Inject constructor(val context: Context) : DIBasePresenter<LoginContract.View>(),LoginContract.Presenter{

    val apiService = APIHelper.getClient().create(APIService::class.java)

    override fun checkLogin(username: String, password: String) {

        apiService.postLogin(username,password).enqueue(object : Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
                getView()?.returnLogin(false,null,"Username or Password Salah")
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if(response.isSuccessful){
                    val header = HashMap<String,String>()
                   // header.set("Authorization",response.body()?.token!!)
                    header.set("Authorization","eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzUxMiJ9.eyJpZCI6IjIwOSIsImlzcyI6IjE0OS4xMjkuMjEzLjMwIiwiaWF0IjoxNTQ2OTU3NDU1LCJuYmYiOjE1NDY5NTc0NTUsImV4cCI6MTU0ODE2NzA1NSwibG9ndHlwZSI6IjEifQ.2FXjxDfdD4VsdXngLdKX68egq46vBUm7oP1G9HTZ-sco7FGMwRHj_odT2Y0QNLKUZ7rcxg05ZZi4Mu6hOD4Pqw")
                    APIHelper.Headers  = header
                    SharedPreferenceService(context).saveInt(BKD.LOGINTYPE,response.body()?.logtype!!.toInt())
                    getView()?.returnLogin(true,response.body(),"success")
                }else{
                    getView()?.returnLogin(false,null,"Username or Password Salah")
                }
            }
        })
     }

}