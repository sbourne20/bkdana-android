package rzgonz.bkd.modules.daftar.mikro.upload

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

class DaftarMikroUploadPresenter @Inject constructor(context: Context) : DIBasePresenter<DaftarMikroUploadContract.View>(),DaftarMikroUploadContract.Presenter{

    val apiService = APIHelper.getClient().create(PinjamanService::class.java)
    override fun getPinjaman() {
        apiService.getPengajuanMikro().enqueue(object : Callback<PengajuanResponse> {
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



    override fun sendUpload(resquest: RequestBody) {
            apiService.sendMikro3(resquest).enqueue(object : Callback<BaseResponse<String?>> {
                override fun onFailure(call: Call<BaseResponse<String?>>, t: Throwable) {
                    getView()?.returnUpload(false, null, "Username or Password Salah")
                }

                override fun onResponse(call: Call<BaseResponse<String?>>, response: Response<BaseResponse<String?>>) {
                    if (response.isSuccessful) {
                        getView()?.returnUpload(true, response.body()?.toString(), "success")
                    } else {
                        getView()?.returnUpload(false, null, "no data")
                    }
                }
            })
        }
}