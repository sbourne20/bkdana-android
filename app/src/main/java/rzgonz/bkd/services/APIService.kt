package rzgonz.bkd.services

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST


/**
 * Created by rzgonz on 8/2/17.
 */

interface APIService {
    @FormUrlEncoded
    @POST("home/")
    fun getHome(@Field("token")token:String): Call<String>

    @GET("access/")
    fun getAccess(): Call<String>


}