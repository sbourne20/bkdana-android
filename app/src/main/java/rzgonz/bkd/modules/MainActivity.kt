package rzgonz.bkd.modules

import android.content.Context
import android.os.Bundle
import android.util.Log
import rzgonz.bkd.Apps.APKModel
import rzgonz.bkd.R
import rzgonz.bkd.injector.DaggerUserComponent
import rzgonz.bkd.models.LoginResponse
import rzgonz.bkd.modules.Login.LoginContract
import rzgonz.bkd.modules.Login.LoginPresenter
import rzgonz.core.kotlin.activity.DIBaseActivity
import javax.inject.Inject

class MainActivity : DIBaseActivity(),LoginContract.View {
    override fun getContext(): Context {
        return getContext()
    }

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

    override fun initLayout(): Int {return R.layout.activity_login }

    override fun initUI(savedInstanceState: Bundle?) {
//        btnLoad.setOnClickListener {
//            loginPresenter.checkLogin()
//        }
    }

    override fun returnLogin(status: Boolean, responde: LoginResponse?, message: String) {
      Log.d("Login","${status} -> ${responde} --> ${message} ")

    }


}
