package rzgonz.bkd.modules.transaksi

import android.content.Context
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rzgonz.bkd.models.transaksi.TransaksiResponse
import rzgonz.bkd.models.transaksi.dana.TransaksiDanaResponse
import rzgonz.bkd.services.APIService
import rzgonz.core.kotlin.helper.APIHelper
import rzgonz.core.kotlin.presenter.DIBasePresenter
import javax.inject.Inject

class TransaksiPresenter @Inject constructor(context: Context) : DIBasePresenter<TransaksiContract.View>(),TransaksiContract.Presenter{

    val apiService = APIHelper.getClient().create(APIService::class.java)

    override fun getListTransaksi(limit: Int, page: Int) {
        apiService.getListTransaksi(page,limit).enqueue(object : Callback<TransaksiResponse>{
            override fun onFailure(call: Call<TransaksiResponse>, t: Throwable) {
                Log.d("return fail",t.message)
                getView()?.returnTransaksi(false,null,"Username or Password Salah")
            }

            override fun onResponse(call: Call<TransaksiResponse>, response: Response<TransaksiResponse>) {
                Log.d("retrun suk",response.body().toString())
                if(response.isSuccessful){
                    if(response.body()?.response.equals("fail")){
                        getView()?.returnTransaksi(false,null,"No Data");
                    }else{ getView()?.returnTransaksi(true,response.body(),"success")}

                }else{
                    getView()?.returnTransaksi(false,null,response.errorBody().toString())
                }
            }
        })
    }

    override fun getListTransaksiDana(limit: Int, page: Int) {
        apiService.getListTransaksiDana(page,limit).enqueue(object : Callback<TransaksiDanaResponse>{
            override fun onFailure(call: Call<TransaksiDanaResponse>, t: Throwable) {
                Log.d("return fail",t.message)
                getView()?.returnTransaksiDana(false,null,"Username or Password Salah")
            }

            override fun onResponse(call: Call<TransaksiDanaResponse>, response: Response<TransaksiDanaResponse>) {
                Log.d("retrun suk",response.body().toString())
                if(response.isSuccessful){
                    if(response.body()?.response.equals("fail")){
                        getView()?.returnTransaksiDana(false,null,"No Data");
                    }else{ getView()?.returnTransaksiDana(true,response.body(),"success")}

                }else{
                    getView()?.returnTransaksiDana(false,null,response.errorBody().toString())
                }
            }
        })
    }
}