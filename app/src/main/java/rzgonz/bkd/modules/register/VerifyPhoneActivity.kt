package rzgonz.bkd.modules.register

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import rzgonz.bkd.R
import kotlinx.android.synthetic.main.activity_verify_phone.*
import rzgonz.bkd.modules.home.DashboardActivity


class VerifyPhoneActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_verify_phone)
        otp_view.setOtpCompletionListener {
            Log.d("onOtpCompleted=>", it)
        }
        btnVerify.setOnClickListener {
            startActivity(Intent(baseContext,DashboardActivity::class.java))
        }
    }
}
