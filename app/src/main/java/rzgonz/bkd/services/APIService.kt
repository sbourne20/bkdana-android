package rzgonz.bkd.services

import retrofit2.Call
import retrofit2.http.*
import rzgonz.bkd.models.LoginResponse
import rzgonz.bkd.models.transaksi.TransaksiResponse
import rzgonz.bkd.models.transaksi.detail.DetailTransaksiResponse


/**
 * Created by rzgonz on 8/2/17.
 */

interface APIService {
    @FormUrlEncoded
    @POST("home/")
    fun getHome(@Field("token")token:String): Call<String>

    @GET("access/")
    fun getAccess(): Call<String>

    @GET("transaksi_peminjam/list/")
    fun getListTransaksi(): Call<TransaksiResponse>

    @GET("transaksi_peminjam/detai/")
    fun getDetialTransaksi(@Query("t")id:String): Call<DetailTransaksiResponse>

    @FormUrlEncoded
    @POST("auth/login/")
    fun postLogin(@Field("username")userName:String,@Field("password")passWord:String): Call<LoginResponse>


}