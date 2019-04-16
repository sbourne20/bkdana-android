package rzgonz.bkd.modules.daftar.mikro.usaha

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

class DaftarMikroUsahaPresenter @Inject constructor(context: Context) : DIBasePresenter<DaftarMikroUsahaContract.View>(),DaftarMikroUsahaContract.Presenter{

    val apiService = APIHelper.getClient().create(PinjamanService::class.java)

    override fun sendDataUsaha(dataUsaha: RequestBody) {
            apiService.sendMikro2(dataUsaha).enqueue(object : Callback<BaseResponse<String?>> {
                override fun onFailure(call: Call<BaseResponse<String?>>, t: Throwable) {
                    getView()?.returnDataUsaha(false, null, "Username or Password Salah")
                }

                override fun onResponse(call: Call<BaseResponse<String?>>, response: Response<BaseResponse<String?>>) {
                    if (response.isSuccessful) {
                        if("fail".equals(response.body()?.response)){
                            getView()?.returnDataUsaha(false, null, response.body()?.message!!)
                        }else{
                            getView()?.returnDataUsaha(true, response.body()?.toString(), "success")
                        }
                    } else {
                        getView()?.returnDataUsaha(false, null, "no data")
                    }
                }
            })
        }
}