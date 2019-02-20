package rzgonz.bkd.modules.password

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_confim_password.*
import rzgonz.bkd.R
import rzgonz.bkd.models.LoginResponse
import rzgonz.bkd.modules.Login.LoginContract
import rzgonz.bkd.modules.Login.LoginPresenter
import rzgonz.core.kotlin.activity.DIBaseActivity
import rzgonz.core.kotlin.helper.SharedPreferenceService
import android.app.Activity
import android.content.Intent
import rzgonz.bkd.Apps.APKModel
import rzgonz.bkd.constant.BKD
import rzgonz.bkd.injector.User.DaggerUserComponent
import javax.inject.Inject


class ConfimPasswordActivity : DIBaseActivity(),LoginContract.View {

    @Inject
    lateinit var mPresenter : LoginPresenter
    override fun initLayout(): Int {
        return R.layout.activity_confim_password
    }

    override fun initUI(savedInstanceState: Bundle?) {
        btnConfirm.setOnClickListener {
            showProgressDialog(this,"Mohon Tunggu",false)
            mPresenter.checkPassword(SharedPreferenceService(this).getString(BKD.EMAIL,""),et_password.text.toString())
        }
    }

    override fun inject() {
        DaggerUserComponent.builder().appsComponent(APKModel.appsComponent).build().inject(this)
    }

    override fun onAttachView() {
        mPresenter.attachView(this)
    }

    override fun onDetachView() {
        mPresenter.detachView()
    }

    override fun returnLogin(status: Boolean, responde: LoginResponse?, message: String) {
        progressDialog?.dismiss()
        if(status){
            val returnIntent = Intent()
            returnIntent.putExtra("result", true)
            setResult(Activity.RESULT_OK, returnIntent)
            finish()
        }else{
            showError("Password Salah")
        }
    }
}
