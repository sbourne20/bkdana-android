package rzgonz.bkd.modules.peminjam.detial

import android.content.Context
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rzgonz.bkd.models.BaseResponse
import rzgonz.bkd.models.BaseResponseMessage
import rzgonz.bkd.models.peminjam.detail.PeminjamDetailResponse
import rzgonz.bkd.models.transaksi.TransaksiResponse
import rzgonz.bkd.models.transaksi.detail.DetailTransaksiResponse
import rzgonz.bkd.services.APIService
import rzgonz.bkd.services.PeminjamService
import rzgonz.core.kotlin.helper.APIHelper
import rzgonz.core.kotlin.presenter.DIBasePresenter
import javax.inject.Inject

class DetailPeminjamPresenter: DIBasePresenter<DetailPinjmanContract.View>(),DetailPinjmanContract.Presenter{

    val apiService = APIHelper.getClient().create(PeminjamService::class.java)

    override fun getDetail(id: String) {
        apiService.getDetial(id).enqueue(object : Callback<PeminjamDetailResponse>{
            override fun onFailure(call: Call<PeminjamDetailResponse>, t: Throwable) {
                Log.d("return fail",t.message)
                getView()?.returnDetial(false,null,"${t}")
            }

            override fun onResponse(call: Call<PeminjamDetailResponse>, response: Response<PeminjamDetailResponse>) {
                Log.d("retrun suk",response.body().toString())
                if(response.isSuccessful){
                    getView()?.returnDetial(true,response.body(),"success")
                }else{
                    getView()?.returnDetial(false,null,response.errorBody().toString())
                }
            }
        })
    }

    override fun postPendanaan(id: String, nominal_pendanaan: String) {
        apiService.postPendanaan(id,nominal_pendanaan).enqueue(object :Callback<BaseResponseMessage>{
            override fun onFailure(call: Call<BaseResponseMessage>, t: Throwable) {
                getView()?.retrunPendanaan(false,null,"${t}")
            }

            override fun onResponse(call: Call<BaseResponseMessage>, response: Response<BaseResponseMessage>) {
                if(response.isSuccessful){
                    if(response.body()?.response.equals("fail")){
                        getView()?.retrunPendanaan(false,response.body()?.message,response.body()?.message)
                    }else{
                        getView()?.retrunPendanaan(true,response.body()?.message,response.body()?.message)
                    }

                }else{
                    getView()?.retrunPendanaan(false,null,response.errorBody().toString())
                }
            }
        })
    }
}