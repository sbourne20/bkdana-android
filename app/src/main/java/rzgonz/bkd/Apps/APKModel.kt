package rzgonz.bkd.Apps

import rzgonz.bkd.injector.AppsComponent
import rzgonz.bkd.injector.AppsModule
import rzgonz.bkd.injector.DaggerAppsComponent
import rzgonz.bkd.services.interceptors.AuthTokenInterceptor
import rzgonz.core.kotlin.apps.RzApps
import rzgonz.core.kotlin.helper.APIHelper
import java.text.NumberFormat
import java.util.*

/**
 * Created by rzgonz on 7/12/17.
 */
class APKModel : RzApps {

    constructor() : super()

    init {
        APIHelper.BASE_URL = "http://149.129.213.30/"
       // APIHelper.Authorization ="Basic dXNlcm5hbWU6aW5kb25lc2lhZ28="
//        var header = HashMap<String,String>()
//        header.set("Authorization","Basic cnpnb256OjFxe30hUVtd")
//        APIHelper.Headers  = header
        APIHelper.setAuthInterceptor(AuthTokenInterceptor(this))
       // APIHelper.HOST_NAME = "bkdroid.bknime.com"
       // APIHelper.PUBLIC_KEY_HASH = "sha256/ZtTK4ku9tn5Sq7YqN6piQIbnJvkYoLuTi1/ujEVRkzI="

    }


    override fun onCreate() {
        super.onCreate()
        appsComponent = DaggerAppsComponent.builder().appsModule(AppsModule(applicationContext)).build()
    }

    companion object {
        lateinit var appsComponent :AppsComponent

    }

    fun Int.toThousand(): String {
//        val formatter = NumberFormat.getInstance(Locale.US) as DecimalFormat
//        formatter.applyPattern("#.###")
        return  NumberFormat.getNumberInstance(Locale.GERMANY).format(this)
    }
}
