package rzgonz.bkd.Apps

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import rzgonz.bkd.injector.AppsComponent
import rzgonz.bkd.injector.AppsModule
import rzgonz.bkd.injector.DaggerAppsComponent
import rzgonz.bkd.services.interceptors.AuthTokenInterceptor
import rzgonz.core.kotlin.apps.RzApps
import rzgonz.core.kotlin.helper.APIHelper
import java.text.NumberFormat
import java.util.*
import com.crashlytics.android.Crashlytics
import com.google.firebase.analytics.FirebaseAnalytics
import io.fabric.sdk.android.Fabric
import android.support.v4.content.ContextCompat.getSystemService
import android.widget.EditText
import id.zelory.compressor.Compressor
import java.io.File


/**
 * Created by rzgonz on 7/12/17.
 */
class APKModel : RzApps {

    constructor() : super()

    init {

       // APIHelper.BASE_URL = "http://149.129.248.46/"
       // APIHelper.Authorization ="Basic dXNlcm5hbWU6aW5kb25lc2lhZ28="

        //Localhost
            APIHelper.BASE_URL = "http://192.168.1.86/bkd-api/"

        // APIHelper.Authorization ="Basic dXNlcm5hbWU6aW5kb25lc2lhZ28="

//        var header = HashMap<String,String>()
//        header.set("Authorization","Basic cnpnb256OjFxe30hUVtd")
//        APIHelper.Headers  = header
       // APIHelper.setAuthInterceptor(AuthTokenInterceptor(this))
       // APIHelper.HOST_NAME = "bkdroid.bknime.com"
       // APIHelper.PUBLIC_KEY_HASH = "sha256/ZtTK4ku9tn5Sq7YqN6piQIbnJvkYoLuTi1/ujEVRkzI="

    }


    override fun onCreate() {
        super.onCreate()
        appsComponent = DaggerAppsComponent.builder().appsModule(AppsModule(applicationContext)).build()
//        Fabric.with(this, Crashlytics())
        FirebaseAnalytics.getInstance(this);
    }

    companion object {
        lateinit var appsComponent :AppsComponent

    }


}


fun Int.toThousand(): String {
//        val formatter = NumberFormat.getInstance(Locale.US) as DecimalFormat
//        formatter.applyPattern("#.###")
    return  NumberFormat.getNumberInstance(Locale.US).format(this)
}

fun EditText.dismissKeyboard() {
    val imm = this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(this.windowToken, 0)
}

fun File.compressImage(context: Context):File{
    return Compressor(context).setMaxWidth(800)
            .setMaxHeight(600)
            .setQuality(90).compressToFile(this)
}
