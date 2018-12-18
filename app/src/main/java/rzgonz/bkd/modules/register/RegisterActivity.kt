package rzgonz.bkd.modules.register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import kotlinx.android.synthetic.main.activity_register.*
import rzgonz.bkd.Apps.APKModel
import rzgonz.bkd.R
import rzgonz.bkd.injector.User.DaggerUserComponent
import rzgonz.bkd.models.BaseResponse
import rzgonz.core.kotlin.activity.DIBaseActivity
import java.util.regex.Pattern
import javax.inject.Inject
import android.widget.AdapterView.OnItemSelectedListener



class RegisterActivity : DIBaseActivity(),RegisterContract.View {


    @Inject
    lateinit var mPresenter:RegisterPresenter
    override fun initLayout(): Int {
        return R.layout.activity_register
    }

    var EMAIL_CHECK = false
    var PASS_CHECK = false
    var PHONE_CHECK = false

    override fun initUI(savedInstanceState: Bundle?) {
        btnDaftar.isEnabled = false
        btnDaftar.setOnClickListener {
            showProgressDialog(this, "Register Checking", false)
            if (spRegisterType.selectedItemPosition == 0) {
                mPresenter.sendRegister(etName.text.toString(), etPhone.text.toString(), et_email_login.text.toString(), et_password_login.text.toString(), et_password_login.text.toString(), etSuberDana.text.toString())

            } else {
                mPresenter.sendRegisterPinjam(etName.text.toString(), etPhone.text.toString(), et_email_login.text.toString(), et_password_login.text.toString(), et_password_login.text.toString(), spRegisterType.selectedItemPosition)
            }
        }
        et_email_login.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                if(et_email_login.text.isNotEmpty()){
                  isValidEmail(et_email_login.text.toString())
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

        et_password_login.addTextChangedListener(object :TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                et_password_login.text.let {
                    passValidation(et_password_login.text.toString())
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }
        })

        spRegisterType.setOnItemSelectedListener(object : OnItemSelectedListener {

            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                when(position){
                    0-> till_sumberDana.visibility =View.VISIBLE
                    1-> till_sumberDana.visibility =View.GONE
                    2-> till_sumberDana.visibility =View.GONE
                }

            }

            override fun onNothingSelected(parentView: AdapterView<*>) {

            }

        })


    }

    private fun isValidEmail(email: String) {
        val EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

        val pattern = Pattern.compile(EMAIL_PATTERN)
        val matcher = pattern.matcher(email)
        EMAIL_CHECK = matcher.matches()
        if(!EMAIL_CHECK){
            btnDaftar.visibility = View.INVISIBLE
            til_email.isErrorEnabled = true
            til_email.setBoxBackgroundColorResource(R.color.Red500)
            til_email.error = "Email tidak valid"
                }else{

            til_email.setBoxBackgroundColorResource(android.R.color.white)
            btnDaftar.visibility = View.VISIBLE
            til_email.isErrorEnabled = false

        }
    }
    fun passValidation(pass:String) {
        val pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$"
        PASS_CHECK =pass.matches(pattern.toRegex())
         if (!PASS_CHECK){
             btnDaftar.visibility = View.INVISIBLE
             til_password.isErrorEnabled= true
             til_password.setBoxBackgroundColorResource(R.color.Red500)
             til_password.error ="Paasword minimal 8 charakter terdiri dari angka dan ruhuf dan satu huruf besar "
            }else{
             til_password.setBoxBackgroundColorResource(android.R.color.white)
             btnDaftar.visibility = View.VISIBLE
             til_password.isErrorEnabled= false
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

    override fun returnRegister(status: Boolean, responde: BaseResponse<String>?, message: String) {
        progressDialog?.dismiss()
        if (status){
            startActivity(Intent(baseContext,VerifyPhoneActivity::class.java))
        }else{
            showError(message)
        }
    }
}
