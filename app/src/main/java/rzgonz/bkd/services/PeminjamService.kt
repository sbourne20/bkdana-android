package rzgonz.bkd.services

import retrofit2.Call
import retrofit2.http.*
import rzgonz.bkd.models.BaseResponse
import rzgonz.bkd.models.BaseResponseMessage
import rzgonz.bkd.models.LoginResponse
import rzgonz.bkd.models.bank.ListBankResponse
import rzgonz.bkd.models.dashboard.MySaldoResponse
import rzgonz.bkd.models.dashboard.RepaymentResponse
import rzgonz.bkd.models.koran.KoranListResponse
import rzgonz.bkd.models.peminjam.PeminjamListResponse
import rzgonz.bkd.models.peminjam.detail.PeminjamDetailResponse
import rzgonz.bkd.models.profile.UserProfileResponse
import rzgonz.bkd.models.redem.ListRedemResponse
import rzgonz.bkd.models.topup.ListTopupResponse
import rzgonz.bkd.models.transaksi.TransaksiResponse
import rzgonz.bkd.models.transaksi.detail.DetailTransaksiResponse
import java.util.stream.IntStream


/**
 * Created by rzgonz on 8/2/17.
 */

interface PeminjamService {

    @GET("daftar_peminjam/index")
    fun getListPeminjam(@Query("page")page:Int,@Query("limit")limit:Int): Call<PeminjamListResponse>
    @GET("/transaksi_pendana/detail")
    fun getDetial(@Query("t")id:String): Call<PeminjamDetailResponse>
    @FormUrlEncoded
    @POST("pendanaan/submit")
    fun postPendanaan(@Field("transaksi_id")trasaksId:String,@Field("nominal_pendanaan")nominal_pendanaan:String): Call<BaseResponseMessage>

}