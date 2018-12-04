package rzgonz.bkd.modules.splashscreen

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_home.*
import rzgonz.bkd.R
import rzgonz.bkd.modules.Login.LoginActivity
import rzgonz.bkd.modules.register.RegisterActivity

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        btnLogin.setOnClickListener {
            startActivity(Intent(baseContext,LoginActivity::class.java))
        }
        btnRegister.setOnClickListener {
            startActivity(Intent(baseContext,RegisterActivity::class.java))
        }
    }
}
