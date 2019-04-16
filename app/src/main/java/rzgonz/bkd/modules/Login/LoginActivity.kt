package rzgonz.bkd.modules.Login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import rzgonz.bkd.Apps.APKModel
import rzgonz.bkd.R
import rzgonz.bkd.injector.User.DaggerUserComponent
import rzgonz.bkd.models.LoginResponse
import rzgonz.bkd.modules.home.DashboardActivity
import rzgonz.bkd.modules.register.RegisterActivity
import rzgonz.bkd.modules.splashscreen.HomeActivity
import rzgonz.core.kotlin.activity.DIBaseActivity
import javax.inject.Inject
import android.content.pm.PackageManager
import android.provider.SyncStateContract.Helpers.update
import android.content.pm.PackageInfo
import android.util.Base64
import android.util.Log
import android.view.View
import okhttp3.Interceptor
import rzgonz.bkd.BuildConfig
import rzgonz.bkd.services.interceptors.AuthTokenInterceptor
import rzgonz.bkd.services.interceptors.KosongInterceptor
import rzgonz.core.kotlin.helper.APIHelper
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class LoginActivity : DIBaseActivity(),LoginContract.View {

    @Inject
    lateinit var loginPresenter : LoginPresenter

    override fun inject() {
        DaggerUserComponent.builder().appsComponent(APKModel.appsComponent).build().inject(this)
    }

    override fun onAttachView() {
        loginPresenter.attachView(this)
    }

    override fun onDetachView() {
        loginPresenter.detachView()
    }

    override fun initLayout(): Int {
        return R.layout.activity_login
    }

    override fun initUI(savedInstanceState: Bundle?) {
//        et_email_login.setText("rzgonz@gmail.com")
//        et_password_login.setText("123456abcD")
        if(BuildConfig.DEBUG) {
            et_email_login.setText("andhika.desta88@gmail.com")
            et_password_login.setText("MD123123aabb")
        }


        btnDaftar.setOnClickListener {
            startActivity(Intent(baseContext,RegisterActivity::class.java))
        }

        btnLogin.setOnClickListener {
            showProgressDialog(this,"Please waiting",false)
            loginPresenter.checkLogin(et_email_login.text.toString(),et_password_login.text.toString())
        }
        if(BuildConfig.DEBUG) {
            tvForget.setOnClickListener {
                et_email_login.setText("iriawan.maarif@gmail.com")
                et_password_login.setText("Ab123456")
            }
        }else{
            tvForget.visibility= View.GONE
        }

        btnBack.setOnClickListener {
            finish()
        }


        APIHelper.setAuthInterceptor(KosongInterceptor(this))
        //printhashkey()
    }

    override fun returnLogin(status: Boolean, responde: LoginResponse?, message: String) {
         progressDialog?.dismiss()
        if(status){
            finish()
            startActivity(Intent(baseContext,DashboardActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }else{
            Toast.makeText(baseContext,message, Toast.LENGTH_LONG).show()
        }
    }

    fun printhashkey() {

        try {
            val info = packageManager.getPackageInfo("rzgonz.bkd", PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {

        } catch (e: NoSuchAlgorithmException) {

        }

    }
}
