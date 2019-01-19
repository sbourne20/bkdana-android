package rzgonz.bkd.modules.daftar.kilat.upload

import android.content.Context
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rzgonz.bkd.models.BaseResponse
import rzgonz.bkd.models.pinjaman.PengajuanResponse
import rzgonz.bkd.services.PinjamanService
import rzgonz.core.kotlin.helper.APIHelper
import rzgonz.core.kotlin.presenter.DIBasePresenter
import javax.inject.Inject

class DaftarKilatUploadPresenter @Inject constructor(context: Context) : DIBasePresenter<DaftarKilatUploadContract.View>(),DaftarKilatUploadContract.Presenter{

    val apiService = APIHelper.getClient().create(PinjamanService::class.java)
    override fun getPinjaman() {
        apiService.getPengajuanKilat().enqueue(object : Callback<PengajuanResponse> {
            override fun onFailure(call: Call<PengajuanResponse>?, t: Throwable?) {
                getView()?.retrunPijanam(false,null,"Username or Password Salah")
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
                    getView()?.returnSendUpload(false, null, "Username or Password Salah")
                }

                override fun onResponse(call: Call<BaseResponse<String?>>, response: Response<BaseResponse<String?>>) {
                    if (response.isSuccessful) {
                        getView()?.returnSendUpload(true, response.body()?.toString(), "success")
                    } else {
                        getView()?.returnSendUpload(false, null, "no data")
                    }
                }
            })
        }
}