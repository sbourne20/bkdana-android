package rzgonz.bkd.services

import retrofit2.Call
import retrofit2.http.*
import rzgonz.bkd.models.BaseResponse
import rzgonz.bkd.models.LoginResponse
import rzgonz.bkd.models.bank.ListBankResponse
import rzgonz.bkd.models.provinsi.ProvinsiResponse
import rzgonz.bkd.models.redem.ListRedemResponse
import rzgonz.bkd.models.topup.ListTopupResponse
import rzgonz.bkd.models.transaksi.TransaksiResponse
import rzgonz.bkd.models.transaksi.dana.TransaksiDanaResponse
import rzgonz.bkd.models.transaksi.dana.detail.TransaksiDanaDetailResponse
import rzgonz.bkd.models.transaksi.detail.DetailTransaksiResponse
import rzgonz.bkd.modules.transaksi.dana.TransaksiDanaDetailActivity
import java.util.stream.IntStream


/**
 * Created by rzgonz on 8/2/17.
 */

interface APIService {
    @FormUrlEncoded
    @POST("home/")
    fun getHome(@Field("token")token:String): Call<String>

    @GET("access/")
    fun getAccess(): Call<String>

    @GET("transaksi_peminjam/list")
    fun getListTransaksi(@Query("page")page:Int,@Query("limit")limit:Int): Call<TransaksiResponse>

    @GET("transaksi_peminjam/detail/")
    fun getDetialTransaksi(@Query("t")id:String): Call<DetailTransaksiResponse>


    @GET("transaksi_pendana/list")
    fun getListTransaksiDana(@Query("page")page:Int,@Query("limit")limit:Int): Call<TransaksiDanaResponse>

    @GET("transaksi_pendana/detail/")
    fun getDetialTransaksiDana(@Query("t")id:String): Call<TransaksiDanaDetailResponse>


    @FormUrlEncoded
    @POST("auth/login/")
    fun postLogin(@Field("username")userName:String,@Field("password")passWord:String): Call<LoginResponse>

    @FormUrlEncoded
    @POST("member/cek_password/")
    fun postPassoword(@Field("username")userName:String,@Field("password")passWord:String): Call<LoginResponse>

    @FormUrlEncoded
    @POST("register_peminjam/submit_reg/")
    fun sendRegPinjam(@Field("fullname")fullname:String,@Field("email")email:String,@Field("telp")telp:String,@Field("password")pasword:String,@Field("confirm_password")confirm_password:String,@Field("type")type:Int): Call<BaseResponse<String>>

    @FormUrlEncoded
    @POST("register_pendana/submit_register/")
    fun sendReg(@Field("fullname")fullname:String,@Field("email")email:String,@Field("telp")telp:String,@Field("password")pasword:String,@Field("confirm_password")confirm_password:String,@Field("sumberdana")sumberdana:String): Call<BaseResponse<String>>

    @GET("redeem/list")
    fun getRedemList(): Call<ListRedemResponse>

    @FormUrlEncoded
    @POST("redeem/submit")
    fun postRedem(@Field("nomor_rekening")nomor_rekening:String,@Field("nama_bank")nama_bank:String,@Field("jml_redeem")jml_redeem:String): Call<BaseResponse<String>>

    @GET("topup/list")
    fun getTopupList(@Query("limit")limit:Int,@Query("page")page:Int): Call<ListTopupResponse>

    @FormUrlEncoded
    @POST("topup/submit")
    fun postTopup(@Field("nama_rekening")nama_rekening:String,@Field("nomor_rekening")nomor_rekening:String, @Field("nama_bank")nama_bank:String, @Field("jumlah_topup")jumlah_topup:String): Call<BaseResponse<String>>

    @GET("bank/list")
    fun getListBank(): Call<ListBankResponse>

    @FormUrlEncoded
    @POST("pembayaran_cicilan/submit")
    fun postAngsuran(@Field("transaksi_id")transaksi_id:String,@Field("jml_bayar")jml_bayar:String): Call<BaseResponse<String>>

    @FormUrlEncoded
    @POST("member/check_notelp")
    fun postPhone(@Field("no_telp")no_telp:String): Call<BaseResponse<String>>


}