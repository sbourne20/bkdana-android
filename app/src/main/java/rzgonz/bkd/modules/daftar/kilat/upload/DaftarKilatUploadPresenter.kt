package rzgonz.bkd.modules.daftar.kilat.upload

import android.content.Context
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rzgonz.bkd.models.BaseResponse
import rzgonz.bkd.models.checking.CheckPinjamanResponse
import rzgonz.bkd.models.pinjaman.PengajuanResponse
import rzgonz.bkd.services.DashboardService
import rzgonz.bkd.services.PinjamanService
import rzgonz.core.kotlin.helper.APIHelper
import rzgonz.core.kotlin.presenter.DIBasePresenter
import javax.inject.Inject

class DaftarKilatUploadPresenter @Inject constructor(context: Context) : DIBasePresenter<DaftarKilatUploadContract.View>(),DaftarKilatUploadContract.Presenter{

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
        apiService.getPengajuanKilat().enqueue(object : Callback<PengajuanResponse> {
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

    override fun sendUpload(content: RequestBody) {
            apiService.sendKilat3(content).enqueue(object : Callback<BaseResponse<String?>> {
                override fun onFailure(call: Call<BaseResponse<String?>>, t: Throwable) {
                    getView()?.returnSendUpload(false, null, "Maaf Terjadi Kesalahan Pada Sistem Kami")
                }

                override fun onResponse(call: Call<BaseResponse<String?>>, response: Response<BaseResponse<String?>>) {
                    if (response.isSuccessful) {
                        if("fail".equals(response.body()?.response)){
                            getView()?.returnSendUpload(false, null, response.body()?.message!!)
                        }else{
                            getView()?.returnSendUpload(true, response.body()?.toString(), "success")
                        }
                    } else {
                        getView()?.returnSendUpload(false, null, "no data")
                    }
                }
            })
        }
}