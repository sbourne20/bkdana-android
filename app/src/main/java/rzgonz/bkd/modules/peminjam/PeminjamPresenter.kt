package rzgonz.bkd.modules.peminjam

import android.content.Context
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rzgonz.bkd.models.BaseResponse
import rzgonz.bkd.models.bank.ListBankResponse
import rzgonz.bkd.models.peminjam.PeminjamListResponse
import rzgonz.bkd.models.redem.ListRedemResponse
import rzgonz.bkd.models.topup.ListTopupResponse
import rzgonz.bkd.services.APIService
import rzgonz.bkd.services.PeminjamService
import rzgonz.core.kotlin.helper.APIHelper
import rzgonz.core.kotlin.presenter.DIBasePresenter
import javax.inject.Inject

class PeminjamPresenter : DIBasePresenter<PeminjamContract.View>(),PeminjamContract.Presenter {

    val apiService = APIHelper.getClient().create(PeminjamService::class.java)

    override fun getListPeminjam(limit: Int, page: Int) {
        apiService.getListPeminjam(page, limit).enqueue(object : Callback<PeminjamListResponse> {
            override fun onFailure(call: Call<PeminjamListResponse>, t: Throwable) {
                getView()?.retrunListPeminjam(false, null, "error connection")
            }

            override fun onResponse(call: Call<PeminjamListResponse>, response: Response<PeminjamListResponse>) {
                if(response.isSuccessful){
                    if(response.body()?.response.equals("success"))
                        getView()?.retrunListPeminjam(true,response.body()?.content?.listPeminjam,"success")
                    else
                        getView()?.retrunListPeminjam(false,null,"Tidak Ada Data")
                }else{
                    getView()?.retrunListPeminjam(false,null,"Tidak Ada Data")
                }
            }
        })
    }

}