package rzgonz.bkd.modules.transaksi.dana

import android.content.Context
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rzgonz.bkd.models.BaseResponse
import rzgonz.bkd.models.transaksi.TransaksiResponse
import rzgonz.bkd.models.transaksi.dana.TransaksiDanaResponse
import rzgonz.bkd.models.transaksi.dana.detail.TransaksiDanaDetailResponse
import rzgonz.bkd.models.transaksi.detail.DetailTransaksiResponse
import rzgonz.bkd.services.APIService
import rzgonz.core.kotlin.helper.APIHelper
import rzgonz.core.kotlin.presenter.DIBasePresenter
import javax.inject.Inject

class DetailTransaksiDanaPresenter : DIBasePresenter<DetailTransaksiDanaContract.View>(),DetailTransaksiDanaContract.Presenter{

    val apiService = APIHelper.getClient().create(APIService::class.java)

    override fun getDetail(id: String) {
        apiService.getDetialTransaksiDana(id).enqueue(object : Callback<TransaksiDanaDetailResponse>{
            override fun onFailure(call: Call<TransaksiDanaDetailResponse>, t: Throwable) {
                Log.d("return fail",t.message)
                getView()?.returnDetialTransaksi(false,null,"error connection")
            }

            override fun onResponse(call: Call<TransaksiDanaDetailResponse>, response: Response<TransaksiDanaDetailResponse>) {
                Log.d("retrun suk",response.body().toString())
                if(response.isSuccessful){
                    getView()?.returnDetialTransaksi(true,response.body(),"success")
                }else{
                    getView()?.returnDetialTransaksi(false,null,response.errorBody().toString())
                }
            }
        })
    }

}