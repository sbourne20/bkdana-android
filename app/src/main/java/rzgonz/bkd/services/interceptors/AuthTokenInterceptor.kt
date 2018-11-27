package rzgonz.bkd.services.interceptors

import android.content.Context
import android.text.TextUtils
import android.util.Log
import okhttp3.*
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import javax.net.ssl.HttpsURLConnection


/**
 * Created by NAZAR on 30/07/2016.
 */
class AuthTokenInterceptor(private val mContext: Context) : Interceptor {
    private val isGw: Boolean = false

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val refreshTokenUrl = "https://api.bkdroid.com/api/access"
        val original = chain.request()
        var response = chain.proceed(original)

        Log.d("bkdroid", "intercape")
        if (response.code() == HttpsURLConnection.HTTP_UNAUTHORIZED) {
            val refreshToken = "Refresh token"
            if (!TextUtils.isEmpty(refreshToken)) {
                val refreshTokenRequest: Request
                val refreshTokenResponse: Response
                Log.d("bkdroid", "refresh token")
                val requestBody = getRequestBody(refreshToken)
                val requestBuilder = original.newBuilder()
                refreshTokenRequest = requestBuilder.url(refreshTokenUrl).get().build()
                refreshTokenResponse = chain.proceed(refreshTokenRequest)
                Log.d("bkdroid", " respond " + refreshTokenResponse.toString())
                Log.d("bkdroid", " respond " + refreshTokenResponse.toString())

                if (refreshTokenResponse.code() == HttpsURLConnection.HTTP_FORBIDDEN || refreshTokenResponse.code() == HttpsURLConnection.HTTP_UNAUTHORIZED) {
                    forceLogout()
                    return response
                }

                val data = getJsonData(refreshTokenResponse) ?: return response

                processResult(data)

                val newOriginalRequest = reWriteRequest(original) ?: return response
                response = chain.proceed(newOriginalRequest)
            }
        }

        return response
    }

    @Throws(IOException::class)
    private fun getJsonData(refreshTokenResponse: Response): JSONObject? {
        var data: JSONObject? = null
        try {
            if (refreshTokenResponse.body() != null) {
                val refreshTokenObject = JSONObject(refreshTokenResponse.body()!!.string())
                if (refreshTokenObject.optJSONArray("data") != null) {
                    data = refreshTokenObject.optJSONArray("data").getJSONObject(0)
                }
            }
        } catch (ex: JSONException) {
            ex.printStackTrace()
        }

        return data
    }

    private fun reWriteRequest(originalRequest: Request): Request? {
        Log.d("bkdroid", "ulang")
        val accessToken = "ACCES TOKEN"
        return originalRequest.newBuilder().removeHeader("Authorization").addHeader("Authorization", accessToken).build()
    }

    private fun getRequestBody(refreshToken: String): RequestBody {
        return FormBody.Builder()
                .add("FIELD_REFRESH_TOKE", refreshToken)
                .add("FIELD_CLIENT_ID", "ID")
                .add("FIELD_CLIENT_SECRET", "KEY")
                .build()
    }

    private fun processResult(data: JSONObject) {
        Log.d("bkdroid", "procee result")
    }

    private fun forceLogout() {
        Log.d("bkdroid", "logout")
    }

    companion object {

        private val TAG = AuthTokenInterceptor::class.java.simpleName
    }
}
