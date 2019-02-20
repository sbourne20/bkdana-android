package rzgonz.bkd.modules.profile

import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_edit_profile.*
import rzgonz.bkd.R
import rzgonz.bkd.models.profile.UserProfileResponse
import rzgonz.bkd.models.provinsi.ProvinsiItem
import rzgonz.bkd.models.provinsi.ProvinsiResponse
import rzgonz.bkd.modules.profile.adapter.ProvinsiAdapter
import rzgonz.core.kotlin.activity.DIBaseActivity

class EditProfileActivity : DIBaseActivity(),EditProfileContract.View {

    val mPresenter = EditProfilePresenter()

    val listProvinsi = ArrayList<ProvinsiItem>()

    var provinsi = ""

    override fun inject() {

    }

    override fun onAttachView() {
        mPresenter.attachView(this)
    }

    override fun onDetachView() {
        mPresenter.detachView()
    }

    override fun initLayout(): Int {
        return R.layout.activity_edit_profile
    }

    override fun onResume() {
        super.onResume()
        showProgressDialog(this,"Mohon tunggu",false)
        mPresenter.getProfile()
    }

    override fun initUI(savedInstanceState: Bundle?) {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        llAkun.visibility = View.GONE
        llAlamat.visibility = View.GONE
        lllPassword.visibility = View.GONE
        supportActionBar?.setTitle("Ubah Profil")
        supportActionBar?.setSubtitle("Isi Profil Anda Sesuai Dengan Identitas Anda")
        mPresenter.getProfile()

    }

    override fun returnProfile(status: Boolean, responde: UserProfileResponse?, message: String) {
        progressDialog?.dismiss()
        if(status){
            setData(responde)
        }else{
            showMessage(message)
        }
    }

    private fun setData(responde: UserProfileResponse?) {
        etNama.setText(responde?.content?.mumFullname)
        etEmial.setText(responde?.content?.mumEmail)
        etPhone.setText(responde?.content?.mumTelp)
        val listBank = resources.getStringArray(R.array.array_bank)
        listBank.forEachIndexed { index, s ->
            if(s.equals(responde?.content?.namaBank)){
                spBank.setSelection(index)
            }
        }
        etNoRek.setText(responde?.content?.nomorRekening)
        etAlamat.setText(responde?.content?.alamat)
        etKota.setText(responde?.content?.kota)
        provinsi = "${responde?.content?.provinsi}"

        etKodePos.setText(responde?.content?.kodepos)
        btnUpdateAkun.setOnClickListener {
            if(checkAkun()){
                showProgressDialog(this,"Mohon tunggu",false)
                mPresenter.postEditAkun(etNama.text.toString(),etEmial.text.toString(),etPhone.text.toString(),etNoRek.text.toString(),spBank.selectedItem.toString())
            }

        }
        btnUpdatePassword.setOnClickListener {
            if(checkPass()){
                showProgressDialog(this,"Mohon tunggu",false)
                mPresenter.postEditPassword(responde?.content?.idModUserMember!!,etOldPass.text.toString(),etNewPass.text.toString(),etPassKonfirm.text.toString())
            }
        }
        btnUpdateAlamat.setOnClickListener {
            if(checkAlamat()){
                showProgressDialog(this,"Mohon tunggu",false)
                mPresenter.postEditAlamat(responde?.content?.idModUserMember!!,etAlamat.text.toString(),etKota.text.toString(),listProvinsi.get(spProvinsi.selectedItemPosition).provinceName!!,etKodePos.text.toString())
            }

        }

        btnAkun.setOnClickListener {
            when(llAkun.visibility){
                View.GONE -> llAkun.visibility = View.VISIBLE
                View.VISIBLE -> llAkun.visibility = View.GONE
            }
        }

        btnPassword.setOnClickListener {
            when(lllPassword.visibility){
                View.GONE -> lllPassword.visibility = View.VISIBLE
                View.VISIBLE -> lllPassword.visibility = View.GONE
            }
        }

        btnAlamat.setOnClickListener {
            when(llAlamat.visibility){
                View.GONE -> llAlamat.visibility = View.VISIBLE
                View.VISIBLE -> llAlamat.visibility = View.GONE
            }
        }
        mPresenter.getProvinsi()
    }

    private fun checkAkun(): Boolean {

        if(etNama.text.isNullOrEmpty()){
            etNama.setError( "is required!" )
            return  false
        }

        if(etPhone.text.isNullOrEmpty()){
            etNama.setError( "is required!" )
            return  false
        }

        if(etEmial.text.isNullOrEmpty()){
            etEmial.setError( "is required!" )
            return  false
        }
        if(spBank.selectedItemPosition ==0){
            showError("Bank Belum Dipilih")
            return  false
        }

        if(etNoRek.text.isNullOrEmpty()){
            etNoRek.setError( "is required!" )
            return  false
        }
        return true
    }

    private fun checkPass(): Boolean {

        if(etOldPass.text.isNullOrEmpty()){
            etOldPass.setError( "is required!" )
            return  false
        }
        if(etNewPass.text.isNullOrEmpty()){
            etNewPass.setError( "is required!" )
            return  false
        }
        if(etPassKonfirm.text.isNullOrEmpty()){
            etPassKonfirm.setError( "is required!" )
            return  false
        }

        if(!etNewPass.text.toString().equals(etPassKonfirm.text.toString())){
            etNewPass.setError( "Tidak Sama" )
            etPassKonfirm.setError( "Tidak Sama" )
            return  false
        }

        return true
    }

    private fun checkAlamat(): Boolean {

        if(etAlamat.text.isNullOrEmpty()){
            etAlamat.setError( "is required!" )
            return  false
        }
        if(etKota.text.isNullOrEmpty()){
            etKota.setError( "is required!" )
            return  false
        }
        if(etKodePos.text.isNullOrEmpty()){
            etKodePos.setError( "is required!" )
            return  false
        }

        return true
    }

    override fun returnEditAkun(status: Boolean, responde: String?, message: String) {
        progressDialog?.dismiss()
       if(status){
           showMessage(responde!!)
       }else{
           showMessage(message)
       }
    }

    override fun returnProvinsi(status: Boolean, responde: ProvinsiResponse?, message: String) {
        if(status){
            var inde = 0
            listProvinsi.add(ProvinsiItem("Pilih Provinsi"))
            responde?.content?.forEachIndexed { index, provinsiItem ->
                listProvinsi.add(provinsiItem!!)
                if(provinsi.equals(provinsiItem.provinceName)){
                    inde= index+1
                }
            }
        val adater = ProvinsiAdapter(baseContext,listProvinsi.toList())
        spProvinsi.adapter= adater
            spProvinsi.setSelection(inde)
        }else{
            showMessage(message)
        }
    }
}
