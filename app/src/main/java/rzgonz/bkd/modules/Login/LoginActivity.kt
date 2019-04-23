package rzgonz.bkd.modules.Login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_login.*
import rzgonz.bkd.Apps.APKModel
import rzgonz.bkd.R
import rzgonz.bkd.injector.User.DaggerUserComponent
import rzgonz.bkd.models.LoginResponse
import rzgonz.bkd.modules.home.DashboardActivity
import rzgonz.bkd.modules.register.RegisterActivity
import rzgonz.core.kotlin.activity.DIBaseActivity
import javax.inject.Inject
import android.content.pm.PackageManager
import android.util.Base64
import android.util.Log
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.iid.FirebaseInstanceId
import rzgonz.bkd.services.APIService
import rzgonz.bkd.services.interceptors.KosongInterceptor
import rzgonz.core.kotlin.helper.APIHelper
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

const val TAG = "MyToken"
var tokenz = ""

class LoginActivity : DIBaseActivity(), LoginContract.View {

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
        FirebaseInstanceId.getInstance().instanceId
                .addOnCompleteListener(OnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Log.w(TAG, "getInstanceId failed", task.exception)
                        return@OnCompleteListener
                    }

                    // Get new Instance ID token
                    tokenz = task.result!!.token
                })
        btnDaftar.setOnClickListener {
            startActivity(Intent(baseContext,RegisterActivity::class.java))
        }

        btnLogin.setOnClickListener {
            showProgressDialog(this,"Please waiting",false)
            loginPresenter.checkLogin(et_email_login.text.toString(),et_password_login.text.toString(), tokenz)
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
