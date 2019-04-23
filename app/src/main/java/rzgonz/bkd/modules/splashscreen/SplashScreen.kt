package rzgonz.bkd.modules.splashscreen

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import rzgonz.bkd.R
import rzgonz.bkd.constant.BKD
import rzgonz.bkd.modules.home.DashboardActivity
import rzgonz.bkd.modules.home.gocek
import rzgonz.bkd.modules.transaksi.TransaksiFragment
import rzgonz.core.kotlin.helper.APIHelper
import rzgonz.core.kotlin.helper.SharedPreferenceService

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler().postDelayed({
            //doSomethingHere()
            val token =  SharedPreferenceService(this).getString(BKD.TOKEN,"kosong")
            if(token.equals("kosong")){
                startActivity(Intent(baseContext,WorktourActivity::class.java))
            }else{
                val header = HashMap<String,String>()
                header.set("Authorization",token)
                APIHelper.Headers  = header
                startActivity(Intent(baseContext, DashboardActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP))
            }

        }, 2000)
//        val bundle = intent.extras
//        if (bundle != null) {
//            for (key in bundle.keySet()) {
//                val value = bundle.get(key)
//                if (value!!.toString().equals("checking_transaksi")){
////                    finish()
////                    Toast.makeText(this, "testing", Toast.LENGTH_SHORT).show()
////                    supportFragmentManager.beginTransaction().replace(R.id.frame, TransaksiFragment()).commit()
//                     gocek="transaksi"
//
//                }
//            }
//        }
    }
}
