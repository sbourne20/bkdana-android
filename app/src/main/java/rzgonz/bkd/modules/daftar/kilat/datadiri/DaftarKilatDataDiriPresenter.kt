package rzgonz.bkd.modules.daftar.kilat.datadiri

import android.content.Context
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rzgonz.bkd.models.BaseResponse
import rzgonz.bkd.services.PinjamanService
import rzgonz.core.kotlin.helper.APIHelper
import rzgonz.core.kotlin.presenter.DIBasePresenter
import javax.inject.Inject

class DaftarKilatDataDiriPresenter @Inject constructor(context: Context) : DIBasePresenter<DaftarKilatDataDiriContract.View>(),DaftarKilatDataDiriContract.Presenter{

    val apiService = APIHelper.getClient().create(PinjamanService::class.java)

    override fun sendDataDiri(pendidikan: String, nama_perusahaan: String, telp_perusahaan: String, status_karyawan: String, lama_bekerja: String, nama_atasan: String, referensi_1: String, referensi_2: String, namaReferensi: String, namaReferensi2: String) {
        apiService.sendKilat2(pendidikan,nama_perusahaan,telp_perusahaan,status_karyawan,lama_bekerja,nama_atasan,referensi_1,referensi_2,namaReferensi,namaReferensi2)
                .enqueue(object : Callback<BaseResponse<String>> {
                    override fun onFailure(call: Call<BaseResponse<String>>?, t: Throwable?) {
                        getView()?.returnSendDataDiri(false,null,"Maaf Terjadi Kesalahan Pada Sistem Kami")
                    }

                    override fun onResponse(call: Call<BaseResponse<String>>, response: Response<BaseResponse<String>>) {
                        if(response.isSuccessful){
                            getView()?.returnSendDataDiri(true,response.body()?.message,"success")
                        }else{
                            getView()?.returnSendDataDiri(false,null,"no data")
                        }
                    }
                })
    }


}