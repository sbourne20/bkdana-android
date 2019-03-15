package rzgonz.bkd.modules.koran

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rzgonz.bkd.models.koran.KoranListResponse
import rzgonz.bkd.services.APIService
import rzgonz.bkd.services.KoranService
import rzgonz.core.kotlin.helper.APIHelper
import rzgonz.core.kotlin.presenter.DIBasePresenter
import javax.inject.Inject

class KoranPresenter: DIBasePresenter<KoranContract.View>(),KoranContract.Presenter{

    val apiService = APIHelper.getClient().create(KoranService::class.java)

    override fun getKoranList() {
        apiService.getListkoran().enqueue(object : Callback<KoranListResponse> {
            override fun onFailure(call: Call<KoranListResponse>?, t: Throwable?) {
                getView()?.returnKoranList(false,null,"error connection")
            }

            override fun onResponse(call: Call<KoranListResponse>, response: Response<KoranListResponse>) {
                if(response.isSuccessful){
                    getView()?.returnKoranList(true,response.body(),"success")
                }else{
                    getView()?.returnKoranList(false,null,"Tidak Ada Data")
                }
            }
        })
    }
}