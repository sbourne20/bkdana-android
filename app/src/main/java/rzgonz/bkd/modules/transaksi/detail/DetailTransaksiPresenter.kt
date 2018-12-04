package rzgonz.bkd.modules.transaksi.detail

import android.content.Context
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rzgonz.bkd.models.transaksi.TransaksiResponse
import rzgonz.bkd.models.transaksi.detail.DetailTransaksiResponse
import rzgonz.bkd.services.APIService
import rzgonz.core.kotlin.helper.APIHelper
import rzgonz.core.kotlin.presenter.DIBasePresenter
import javax.inject.Inject

class DetailTransaksiPresenter @Inject constructor(context: Context) : DIBasePresenter<DetailTransaksiContract.View>(),DetailTransaksiContract.Presenter{

    val apiService = APIHelper.getClient().create(APIService::class.java)

    override fun getDetail(id: String) {
        apiService.getDetialTransaksi(id).enqueue(object : Callback<DetailTransaksiResponse>{
            override fun onFailure(call: Call<DetailTransaksiResponse>, t: Throwable) {
                Log.d("return fail",t.message)
                getView()?.returnDetialTransaksi(false,null,"Username or Password Salah")
            }

            override fun onResponse(call: Call<DetailTransaksiResponse>, response: Response<DetailTransaksiResponse>) {
                Log.d("retrun suk",response.body().toString())
                if(response.isSuccessful){
                    getView()?.returnDetialTransaksi(true,response.body(),"success")
                }else{
                    getView()?.returnDetialTransaksi(false,null,"Username or Password Salah")
                }
            }
        })
    }


}