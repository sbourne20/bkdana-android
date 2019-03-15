package rzgonz.bkd.services

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import rzgonz.bkd.models.BaseResponse
import rzgonz.bkd.models.dashboard.MySaldoResponse
import rzgonz.bkd.models.pinjaman.PengajuanResponse
import rzgonz.bkd.models.provinsi.ProvinsiResponse
import rzgonz.bkd.models.user.UserResponse


/**
 * Created by rzgonz on 8/2/17.
 */

interface PinjamanService {

    @POST("member/mydata/")
    fun getMyData(): Call<UserResponse>

    @FormUrlEncoded
    @POST("pinjaman/submit_kilat1/")
    fun sendKilat1(@Field("tempat_lahir")tempat_lahir:String,@Field("jenis_kelamin")jenis_kelamin:String,@Field("tanggal_lahir")tanggal_lahir:String,@Field("alamat")alamat:String,@Field("kota")kota:String,@Field("provinsi")provinsi:String,@Field("kodepos")kodepos:String,@Field("pekerjaan")pekerjaan:String,@Field("no_nik")no_nik:String): Call<BaseResponse<String>>

    @FormUrlEncoded
    @POST("pinjaman/submit_kilat2/")
    fun sendKilat2(@Field("pendidikan") pendidikan: String, @Field("nama_perusahaan") nama_perusahaan: String, @Field("telp_perusahaan") telp_perusahaan: String, @Field("status_karyawan") status_karyawan: String, @Field("lama_bekerja") lama_bekerja: String, @Field("nama_atasan") nama_atasan: String, @Field("referensi_1") referensi_1: String, @Field("referensi_2") referensi_2: String, @Field("referensi_nama_1") namaReferensi: String,@Field("referensi_nama_2")  namaReferensi2: String): Call<BaseResponse<String>>

    @POST("pinjaman/submit_kilat3/")
    fun sendKilat3(@Body file : RequestBody): Call<BaseResponse<String?>>

    @POST("pinjaman/pengajuan_kilat")
    fun getPengajuanKilat(): Call<PengajuanResponse>

    @POST("pinjaman/pengajuan_mikro")
    fun getPengajuanMikro(): Call<PengajuanResponse>

    @FormUrlEncoded
    @POST("pinjaman/submit_mikro1/")
    fun sendMirko1(@Field("tempat_lahir")tempat_lahir:String,@Field("jenis_kelamin")jenis_kelamin:String,@Field("alamat")alamat:String,@Field("kota")kota:String,@Field("provinsi")provinsi:String,@Field("kodepos")kodepos:String): Call<BaseResponse<String>>

    @POST("pinjaman/submit_mikro2/")
    fun sendMikro2(@Body file : RequestBody): Call<BaseResponse<String?>>

    @POST("pinjaman/submit_mikro3/")
    fun sendMikro3(@Body file : RequestBody): Call<BaseResponse<String?>>

    @POST("member/mysaldo")
    fun getMysaldo(): Call<MySaldoResponse>

    @GET("province/list")
    fun getListProvinsi(): Call<ProvinsiResponse>

}