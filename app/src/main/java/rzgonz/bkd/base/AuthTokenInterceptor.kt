package rzgonz.bkd.base

import android.content.Context
import android.content.Intent
import android.text.TextUtils
import android.util.Log
import android.widget.Toast
import okhttp3.*
import rzgonz.bkd.constant.BKD
import rzgonz.bkd.modules.Login.LoginActivity
import rzgonz.core.kotlin.helper.SharedPreferenceService
import java.io.IOException
import javax.net.ssl.HttpsURLConnection


class AuthTokenInterceptor(private val mContext: Context) : Interceptor {
    private val isGw: Boolean = false

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        var response = chain.proceed(original)

        Log.d("bkdroid", "intercape")
        if (response.code() == HttpsURLConnection.HTTP_UNAUTHORIZED ||response.code() == HttpsURLConnection.HTTP_FORBIDDEN) {
           forceLogout()
        }

        return response
    }


    private fun forceLogout() {
        SharedPreferenceService(mContext).saveString(BKD.TOKEN,"kosong")
        Toast.makeText(mContext,"Session Habis Harap Login Kembali",Toast.LENGTH_LONG).show()
        mContext.startActivity(Intent(mContext, LoginActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP))

    }

    companion object {
        private val TAG = AuthTokenInterceptor::class.java.simpleName
    }
}
