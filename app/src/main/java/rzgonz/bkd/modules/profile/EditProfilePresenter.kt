package rzgonz.bkd.modules.profile

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import rzgonz.bkd.constant.BKD
import rzgonz.bkd.models.BaseResponse
import rzgonz.bkd.models.LoginResponse
import rzgonz.bkd.models.profile.UserProfileResponse
import rzgonz.bkd.models.provinsi.ProvinsiResponse
import rzgonz.bkd.services.APIService
import rzgonz.bkd.services.EditProfileService
import rzgonz.core.kotlin.helper.APIHelper
import rzgonz.core.kotlin.helper.SharedPreferenceService
import rzgonz.core.kotlin.presenter.DIBasePresenter
import javax.inject.Inject

class EditProfilePresenter : DIBasePresenter<EditProfileContract.View>(),EditProfileContract.Presenter{

    val apiService = APIHelper.getClient().create(EditProfileService::class.java)

    override fun getProfile() {
        apiService.getEditProfile().enqueue(object :Callback<UserProfileResponse>{
            override fun onFailure(call: Call<UserProfileResponse>, t: Throwable) {
                getView()?.returnProfile(false,null,"${t}")
            }

            override fun onResponse(call: Call<UserProfileResponse>, response: Response<UserProfileResponse>) {
                if(response.isSuccessful){
                    getView()?.returnProfile(true,response.body(),"success")
                }else{
                    getView()?.returnProfile(false,null,"Maaf Terjadi masalah pada sistem")
                }
            }
        })
    }

    override fun postEditAkun(fullname: String, email: String, tlp: String, nomer_rekening: String, nama_bank: String) {
        apiService.postEditAkun(fullname,email,tlp,nomer_rekening,nama_bank).enqueue(object :Callback<BaseResponse<String>>{
            override fun onFailure(call: Call<BaseResponse<String>>, t: Throwable) {
                getView()?.returnEditAkun(false,null,"${t}")
            }

            override fun onResponse(call: Call<BaseResponse<String>>, response: Response<BaseResponse<String>>) {
                if(response.isSuccessful){
                    getView()?.returnEditAkun(true,response.body()?.message,"success")
                }else{
                    getView()?.returnEditAkun(false,null,"Maaf Terjadi masalah pada sistem")
                }
            }
        })
    }

    override fun postEditAlamat(member_id: String, alamat: String, kota: String, provinsi: String, kodepos: String) {
        apiService.postEditAlamat(member_id,alamat,kota,provinsi,kodepos).enqueue(object :Callback<BaseResponse<String>>{
            override fun onFailure(call: Call<BaseResponse<String>>, t: Throwable) {
                getView()?.returnEditAkun(false,null,"${t}")
            }

            override fun onResponse(call: Call<BaseResponse<String>>, response: Response<BaseResponse<String>>) {
                if(response.isSuccessful){
                    getView()?.returnEditAkun(true,response.body()?.message,"success")
                }else{
                    getView()?.returnEditAkun(false,null,"Maaf Terjadi masalah pada sistem")
                }
            }
        })
    }

    override fun postEditPassword(member_id: String, old_pass: String, pass: String, conf: String) {
        apiService.postEditPass(member_id,old_pass,pass,conf).enqueue(object :Callback<BaseResponse<String>>{
            override fun onFailure(call: Call<BaseResponse<String>>, t: Throwable) {
                getView()?.returnEditAkun(false,null,"${t}")
            }

            override fun onResponse(call: Call<BaseResponse<String>>, response: Response<BaseResponse<String>>) {
                if(response.isSuccessful){
                    getView()?.returnEditAkun(true,response.body()?.message,"success")
                }else{
                    getView()?.returnEditAkun(false,null,"Maaf Terjadi masalah pada sistem")
                }
            }
        })
    }

    override fun getProvinsi() {
        apiService.getListProvinsi().enqueue(object : Callback<ProvinsiResponse>{
            override fun onFailure(call: Call<ProvinsiResponse>, t: Throwable) {
                getView()?.returnProvinsi(false,null,"${t}")
            }

            override fun onResponse(call: Call<ProvinsiResponse>, response: Response<ProvinsiResponse>) {
                if(response.isSuccessful){
                    getView()?.returnProvinsi(true,response.body(),"success")
                }else{
                    getView()?.returnProvinsi(false,null,"Maaf Terjadi masalah pada sistem")
                }
            }
        })
    }
}