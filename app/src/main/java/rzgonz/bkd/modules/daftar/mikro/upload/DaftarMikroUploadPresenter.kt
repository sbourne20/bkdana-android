package rzgonz.bkd.modules.daftar.mikro.upload

import android.content.Context
import com.google.gson.Gson
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rzgonz.bkd.models.BaseResponse
import rzgonz.bkd.models.BaseResponseMessage
import rzgonz.bkd.models.checking.CheckPinjamanResponse
import rzgonz.bkd.models.pinjaman.PengajuanResponse
import rzgonz.bkd.services.DashboardService
import rzgonz.bkd.services.PinjamanService
import rzgonz.core.kotlin.helper.APIHelper
import rzgonz.core.kotlin.presenter.DIBasePresenter
import javax.inject.Inject

class DaftarMikroUploadPresenter @Inject constructor(context: Context) : DIBasePresenter<DaftarMikroUploadContract.View>(),DaftarMikroUploadContract.Presenter{

    val apiService = APIHelper.getClient().create(PinjamanService::class.java)
    val api2Service = APIHelper.getClient().create(DashboardService::class.java)

    override fun checkPinjaman() {
        api2Service.checkPinjaman().enqueue(object : Callback<CheckPinjamanResponse> {
            override fun onFailure(call: Call<CheckPinjamanResponse>?, t: Throwable?) {
                getView()?.returnCheckPinjaman(false,null,"error connection")
            }

            override fun onResponse(call: Call<CheckPinjamanResponse>, response: Response<CheckPinjamanResponse>) {
                if(response.isSuccessful){
                    if(response.body()?.response.equals("fail")){
                        getView()?.returnCheckPinjaman(false,"1",response.body()?.message)
                    }else{
                        getView()?.returnCheckPinjaman(true,"2",response.body()?.message)
                    }
                }else{
                    getView()?.returnCheckPinjaman(false,null,"${response.errorBody()}")
                }
            }
        })
    }
    override fun getPinjaman() {
        apiService.getPengajuanMikro().enqueue(object : Callback<PengajuanResponse> {
            override fun onFailure(call: Call<PengajuanResponse>?, t: Throwable?) {
                getView()?.retrunPijanam(false,null,"Maaf Terjadi Kesalahan Pada Sistem Kami")
            }

            override fun onResponse(call: Call<PengajuanResponse>, response: Response<PengajuanResponse>) {
                if(response.isSuccessful){
                    getView()?.retrunPijanam(true,response.body()?.content,"success")
                }else{
                    getView()?.retrunPijanam(false,null,"no data")
                }
            }
        })

    }



    override fun sendUpload(resquest: RequestBody) {
            apiService.sendMikro3(resquest).enqueue(object : Callback<BaseResponse<String?>> {
                override fun onFailure(call: Call<BaseResponse<String?>>, t: Throwable) {
                    getView()?.returnUpload(false, null, "Maaf Terjadi Kesalahan Pada Sistem Kami")
                }

                override fun onResponse(call: Call<BaseResponse<String?>>, response: Response<BaseResponse<String?>>) {
                    if (response.isSuccessful) {
                        if(response.body()?.status?.equals("failed")!!){
                            getView()?.returnUpload(false, null, response.body()?.message!!)
                        }else{
                            getView()?.returnUpload(true, response.body()?.toString(), "success")
                        }
                    } else {
                        val gson = Gson().fromJson(response.errorBody()?.charStream(),BaseResponseMessage::class.java)
                        getView()?.returnUpload(false, null,gson.message)
                    }
                }
            })
        }
}