package rzgonz.bkd.modules.Login

import android.content.Context
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rzgonz.bkd.services.APIService
import rzgonz.core.kotlin.helper.APIHelper
import rzgonz.core.kotlin.presenter.DIBasePresenter
import javax.inject.Inject

class LoginPresenter @Inject constructor(context: Context) : DIBasePresenter<LoginContract.View>(),LoginContract.Presenter{

    val apiService = APIHelper.getClient().create(APIService::class.java)

    override fun checkLogin() {

        apiService.getHome("TOKEk").enqueue(object : Callback<String> {
            override fun onFailure(call: Call<String>?, t: Throwable?) {
                Log.d("data","${t.toString()}")
            }

            override fun onResponse(call: Call<String>?, response: Response<String>?) {
                Log.d("onResponse","${response}")
            }

        })
     }

}