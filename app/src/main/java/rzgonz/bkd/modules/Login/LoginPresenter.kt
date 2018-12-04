package rzgonz.bkd.modules.Login

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rzgonz.bkd.models.LoginResponse
import rzgonz.bkd.services.APIService
import rzgonz.core.kotlin.helper.APIHelper
import rzgonz.core.kotlin.presenter.DIBasePresenter
import javax.inject.Inject

class LoginPresenter @Inject constructor(context: Context) : DIBasePresenter<LoginContract.View>(),LoginContract.Presenter{

    val apiService = APIHelper.getClient().create(APIService::class.java)

    override fun checkLogin(username: String, password: String) {

        apiService.postLogin(username,password).enqueue(object : Callback<LoginResponse> {
            override fun onFailure(call: Call<LoginResponse>?, t: Throwable?) {
                getView()?.returnLogin(false,null,"Username or Password Salah")
            }

            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if(response.isSuccessful){
                    var header = HashMap<String,String>()
                    header.set("Authorization",response.body()?.token!!)
                    APIHelper.Headers  = header
                    getView()?.returnLogin(true,response.body(),"success")
                }else{
                    getView()?.returnLogin(false,null,"Username or Password Salah")
                }
            }
        })
     }

}