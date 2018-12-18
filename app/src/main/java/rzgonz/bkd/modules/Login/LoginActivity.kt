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
        et_email_login.setText("rzgonz@gmail.com")
        et_password_login.setText("123456abcD")
        btnDaftar.setOnClickListener {
            startActivity(Intent(baseContext,RegisterActivity::class.java))
        }
        btnLogin.setOnClickListener {
            showProgressDialog(this,"Please waiting",false)
            loginPresenter.checkLogin(et_email_login.text.toString(),et_password_login.text.toString())
        }
    }

    override fun returnLogin(status: Boolean, responde: LoginResponse?, message: String) {
         progressDialog?.dismiss()
        if(status){
            startActivity(Intent(baseContext,DashboardActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP))
        }else{
            Toast.makeText(baseContext,message, Toast.LENGTH_LONG).show()
        }
    }
}
