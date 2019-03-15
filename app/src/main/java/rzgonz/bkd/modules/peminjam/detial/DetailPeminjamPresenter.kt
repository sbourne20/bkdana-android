package rzgonz.bkd.modules.peminjam.detial

import android.content.Context
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rzgonz.bkd.models.BaseResponse
import rzgonz.bkd.models.BaseResponseMessage
import rzgonz.bkd.models.peminjam.detail.PeminjamDetailResponse
import rzgonz.bkd.services.PeminjamService
import rzgonz.core.kotlin.helper.APIHelper
import rzgonz.core.kotlin.presenter.DIBasePresenter

class DetailPeminjamPresenter: DIBasePresenter<DetailPinjmanContract.View>(),DetailPinjmanContract.Presenter{

    val apiService = APIHelper.getClient().create(PeminjamService::class.java)

    override fun getDetail(id: String) {
        apiService.getDetial(id).enqueue(object : Callback<PeminjamDetailResponse>{
            override fun onFailure(call: Call<PeminjamDetailResponse>, t: Throwable) {
                getView()?.returnDetial(false,null,"error connection")
            }

            override fun onResponse(call: Call<PeminjamDetailResponse>, response: Response<PeminjamDetailResponse>) {
                if(response.isSuccessful){
                //    getView()?.returnDetial(true,response.body()?.content,"success")
                }else{
                    getView()?.returnDetial(false,null,"error connection")
                }
            }
        })
    }

    override fun postPendanaan(id: String, nominal_pendanaan: String) {
        apiService.postPendanaan(id,nominal_pendanaan).enqueue(object :Callback<BaseResponseMessage>{
            override fun onFailure(call: Call<BaseResponseMessage>, t: Throwable) {
                getView()?.retrunPendanaan(false,null,"error connection")
            }

            override fun onResponse(call: Call<BaseResponseMessage>, response: Response<BaseResponseMessage>) {
                if(response.isSuccessful){
                    if(response.body()?.response.equals("fail")){
                        getView()?.retrunPendanaan(false,response.body()?.message,response.body()?.message)
                    }else{
                        getView()?.retrunPendanaan(true,response.body()?.message,response.body()?.message)
                    }

                }else{
                    getView()?.retrunPendanaan(false,null,"Gagal")
                }
            }
        })
    }

    override fun checkbiayai() {
        apiService.checkBiayai().enqueue(object :Callback<BaseResponseMessage>{
            override fun onFailure(call: Call<BaseResponseMessage>, t: Throwable) {
                getView()?.ceckStatus(false,null,"error connection")
            }

            override fun onResponse(call: Call<BaseResponseMessage>, response: Response<BaseResponseMessage>) {
                if(response.isSuccessful){
                    if(response.body()?.response.equals("fail")){
                        getView()?.ceckStatus(false,response.body()?.message,response.body()?.message)
                    }else{
                        getView()?.ceckStatus(true,response.body()?.message,response.body()?.message)
                    }

                }else{
                    getView()?.ceckStatus(false,null,"error connection")
                }
            }
        })
    }
}