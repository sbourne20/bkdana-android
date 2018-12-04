package rzgonz.bkd.modules.transaksi

import android.content.Context
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rzgonz.bkd.models.transaksi.TransaksiResponse
import rzgonz.bkd.services.APIService
import rzgonz.core.kotlin.helper.APIHelper
import rzgonz.core.kotlin.presenter.DIBasePresenter
import javax.inject.Inject

class TransaksiPresenter @Inject constructor(context: Context) : DIBasePresenter<TransaksiContract.View>(),TransaksiContract.Presenter{

    val apiService = APIHelper.getClient().create(APIService::class.java)

    override fun getListTransaksi() {
        apiService.getListTransaksi().enqueue(object : Callback<TransaksiResponse>{
            override fun onFailure(call: Call<TransaksiResponse>, t: Throwable) {
                Log.d("return fail",t.message)
                getView()?.returnTransaksi(false,null,"Username or Password Salah")
            }

            override fun onResponse(call: Call<TransaksiResponse>, response: Response<TransaksiResponse>) {
                Log.d("retrun suk",response.body().toString())
                if(response.isSuccessful){
                    getView()?.returnTransaksi(true,response.body(),"success")
                }else{
                    getView()?.returnTransaksi(false,null,"Username or Password Salah")
                }
            }
        })
    }

}