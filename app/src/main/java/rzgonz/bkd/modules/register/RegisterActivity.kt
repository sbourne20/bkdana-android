package rzgonz.bkd.modules.register

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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
import android.widget.Toast
import com.facebook.accountkit.*
import com.facebook.accountkit.ui.AccountKitActivity
import com.facebook.accountkit.ui.LoginType
import com.facebook.accountkit.ui.AccountKitConfiguration
import io.reactivex.internal.util.NotificationLite.getError


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
        btnDaftar.setOnClickListener {
            if(checkInput()){
                showProgressDialog(this, "Register Checking", false)
                phoneLogin(it)
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

        etPhone.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(p0: Editable?) {
                if(p0.toString().length>4) {
                    mPresenter.sendPhone(p0.toString())
                }
            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }
        })

        tvBantuan.setOnClickListener {
            phoneLogin(it)
        }

    }

    private fun checkInput(): Boolean {
        if(!passValidation(et_password_login.text.toString()))
            return false

        if(!isValidEmail(et_email_login.text.toString()))
            return  false

        if(etPhone.length()<5){
            til_email.isErrorEnabled = true
            etPhone.setError("Nomer tidak valid")
            return false
        }

        if(spRegisterType.selectedItemPosition<1) {
            if (etSuberDana.text.isNullOrEmpty()) {
                till_sumberDana.isErrorEnabled = true
                etSuberDana.setError("Silakan Input Sumber Dana")
                return false
            }
        }

    return true
    }

    private fun sendRegister(){
        if (spRegisterType.selectedItemPosition == 0) {
            mPresenter.sendRegister(etName.text.toString(), etPhone.text.toString(), et_email_login.text.toString(), et_password_login.text.toString(), et_password_login.text.toString(), etSuberDana.text.toString())

        } else {
            mPresenter.sendRegisterPinjam(etName.text.toString(), etPhone.text.toString(), et_email_login.text.toString(), et_password_login.text.toString(), et_password_login.text.toString(), spRegisterType.selectedItemPosition)
        }

    }
    private fun isValidEmail(email: String) : Boolean {
        val EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$"

        val pattern = Pattern.compile(EMAIL_PATTERN)
        val matcher = pattern.matcher(email)
        EMAIL_CHECK = matcher.matches()
        if(!EMAIL_CHECK){
            til_email.isErrorEnabled = true
            til_email.setBoxBackgroundColorResource(R.color.Red500)
            til_email.error = "Email tidak valid"
            return false
                }else{
            til_email.setBoxBackgroundColorResource(android.R.color.white)
            btnDaftar.visibility = View.VISIBLE
            til_email.isErrorEnabled = false
            return  true
        }
    }
    fun passValidation(pass:String):Boolean {
        val pattern = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=\\S+$).{8,}$"
        PASS_CHECK =pass.matches(pattern.toRegex())
         if (!PASS_CHECK){
             til_password.isErrorEnabled= true
             til_password.setBoxBackgroundColorResource(R.color.Red500)
             til_password.error ="Paasword minimal 8 charakter terdiri dari angka dan ruhuf dan satu huruf besar "
             return false
            }else{
             til_password.setBoxBackgroundColorResource(android.R.color.white)
             btnDaftar.visibility = View.VISIBLE
             til_password.isErrorEnabled= false
             return true
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
            finish()
        }else{
            showError(message)
        }
    }

    var APP_REQUEST_CODE = 99

    fun getCurrentPhone(){
        Log.d("Account","getCurrentPhone")
        AccountKit.getCurrentAccount(object : AccountKitCallback<Account> {
            override fun onSuccess(account: Account) {
                Log.d("Account","${account}")
              //  val accountKitId = account.getId()
                val phoneNumber = account.getPhoneNumber()
                //val phoneNumberString = phoneNumber.toString()
                etPhone.setText("+62${account.phoneNumber.phoneNumber}")
                sendRegister()
            }

            override fun onError(error: AccountKitError) {
                // Handle Error
                Log.d("Account","${error}")
            }
        })
    }

    fun phoneLogin(view: View) {
        Toast.makeText(baseContext,"SMS",Toast.LENGTH_SHORT).show()
        val intent = Intent(this, AccountKitActivity::class.java)
        val configurationBuilder = AccountKitConfiguration.AccountKitConfigurationBuilder(
                LoginType.PHONE,
                AccountKitActivity.ResponseType.TOKEN) // or .ResponseType.TOKEN
        // ... perform additional configuration ...
        configurationBuilder.setInitialPhoneNumber(PhoneNumber("+62",etPhone.text.toString()))

        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build())


        startActivityForResult(intent, APP_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data != null){
        if (requestCode == APP_REQUEST_CODE) { // confirm that this response matches your request
            val loginResult = data.getParcelableExtra<AccountKitLoginResult>(AccountKitLoginResult.RESULT_KEY)
            val toastMessage: String
            if (loginResult.error != null) {
                toastMessage = loginResult.error!!.errorType.message
            } else if (loginResult.wasCancelled()) {
                toastMessage = "Login Cancelled"
            } else {
                if (loginResult.accessToken != null) {
                    toastMessage = "Success"
                    getCurrentPhone()
                } else {
                    toastMessage = "Success"
                }

            }

            // Surface the result to your user in an appropriate way.
            Toast.makeText(this, toastMessage, Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun returnPhone(status: Boolean, responde: String?, message: String) {
        if(status){
            til_phone.isErrorEnabled = false
        }else{
            til_phone.isErrorEnabled = true
            etPhone.setError("Nomer Telah Digunakan")
            etPhone.requestFocus()
        }
    }
}
