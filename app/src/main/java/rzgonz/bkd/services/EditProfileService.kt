package rzgonz.bkd.services

import retrofit2.Call
import retrofit2.http.*
import rzgonz.bkd.models.BaseResponse
import rzgonz.bkd.models.LoginResponse
import rzgonz.bkd.models.bank.ListBankResponse
import rzgonz.bkd.models.profile.UserProfileResponse
import rzgonz.bkd.models.provinsi.ProvinsiResponse
import rzgonz.bkd.models.redem.ListRedemResponse
import rzgonz.bkd.models.topup.ListTopupResponse
import rzgonz.bkd.models.transaksi.TransaksiResponse
import rzgonz.bkd.models.transaksi.detail.DetailTransaksiResponse
import rzgonz.bkd.models.user.UserResponse
import java.util.stream.IntStream


/**
 * Created by rzgonz on 8/2/17.
 */

interface EditProfileService {

    @FormUrlEncoded
    @POST("profile/update_password")
    fun postEditPass(@Field("member_id")member_id:String,@Field("old_password")old_password:String, @Field("password")password:String, @Field("conf_password")conf_password:String): Call<BaseResponse<String>>
    @FormUrlEncoded
    @POST("profile/update_informasialamat")
    fun postEditAlamat(@Field("member_id")member_id:String,@Field("alamat")alamat:String, @Field("kota")kota:String, @Field("provinsi")provinsi:String, @Field("kodepos")kodepos:String): Call<BaseResponse<String>>

    @FormUrlEncoded
    @POST("profile/update_informasiakun")
    fun postEditAkun(@Field("fullname")fullname:String,@Field("email")email:String, @Field("telp")telp:String, @Field("nomor_rekening")nomor_rekening:String, @Field("nama_bank")nama_bank:String,@Field("nik")nik:String,@Field("jenis_kelamin")jenis_kelamin:String,@Field("tgl_lahir")tgl_lahir:String,@Field("pendidikan")pendidikan:String,@Field("pekerjaan")pekerjaan:String): Call<BaseResponse<String>>


    @POST("member/mydata")
    fun getEditProfile(): Call<UserResponse>
    @GET("province/list")
    fun getListProvinsi(): Call<ProvinsiResponse>

}