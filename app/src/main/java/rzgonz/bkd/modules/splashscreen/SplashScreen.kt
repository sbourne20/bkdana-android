package rzgonz.bkd.modules.splashscreen

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import rzgonz.bkd.R

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        Handler().postDelayed({
            //doSomethingHere()
            startActivity(Intent(baseContext,WorktourActivity::class.java))
        }, 3000)
    }
}
