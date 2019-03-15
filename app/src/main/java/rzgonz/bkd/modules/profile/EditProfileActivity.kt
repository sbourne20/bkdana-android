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
import android.app.DatePickerDialog
import android.view.MotionEvent
import rzgonz.bkd.Apps.dismissKeyboard
import rzgonz.bkd.models.user.UserResponse
import java.util.*
import java.text.SimpleDateFormat


class EditProfileActivity : DIBaseActivity(),EditProfileContract.View {

    val mPresenter = EditProfilePresenter()

    val listProvinsi = ArrayList<ProvinsiItem>()

    var provinsi = ""
    val myCalendar = Calendar.getInstance()

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


        val date = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, monthOfYear)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            updateLabel()
        }

        etTanggalLahir.setOnTouchListener(object : View.OnTouchListener{
            override fun onTouch(p0: View?, p1: MotionEvent?): Boolean {
                if(p1?.action == MotionEvent.ACTION_DOWN){
                    DatePickerDialog(this@EditProfileActivity, date, myCalendar
                            .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                            myCalendar.get(Calendar.DAY_OF_MONTH)).show()

                }
                etTanggalLahir.dismissKeyboard()
                return false
            }
        })
    }


    private fun updateLabel() {
        val myFormat = "dd-MM-yyyy" //In which you need put here
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        etTanggalLahir.isCursorVisible = false
        etTanggalLahir.setText(sdf.format(myCalendar.getTime()))
    }

    override fun returnProfile(status: Boolean, responde: UserResponse?, message: String) {
        progressDialog?.dismiss()
        if(status){
            setData(responde)
        }else{
            showMessage(message)
        }
    }

    private fun setData(responde: UserResponse?) {
        etNama.setText(responde?.content?.namaPengguna)
        etEmial.setText(responde?.content?.email)
        etPhone.setText(responde?.content?.mobileno)
        etNik.setText(responde?.content?.nomorNik)
        etTanggalLahir.setText(responde?.content?.tanggalLahir)
        if(!responde?.content?.jenisKelamin?.toLowerCase().equals("pria")){
            spGender.setSelection(1)
        }
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
                mPresenter.postEditAkun(etNama.text.toString(),etEmial.text.toString(),etPhone.text.toString(),etNoRek.text.toString(),spBank.selectedItem.toString(),etNik.text.toString(),spGender.selectedItem.toString(),etTanggalLahir.text.toString(),spPekerjaan.selectedItemPosition.toString(),spPendidikan.selectedItemPosition.toString())
            }

        }
        btnUpdatePassword.setOnClickListener {
            if(checkPass()){
                showProgressDialog(this,"Mohon tunggu",false)
                mPresenter.postEditPassword(responde?.content?.memberId!!,etOldPass.text.toString(),etNewPass.text.toString(),etPassKonfirm.text.toString())
            }
        }
        btnUpdateAlamat.setOnClickListener {
            if(checkAlamat()){
                showProgressDialog(this,"Mohon tunggu",false)
                mPresenter.postEditAlamat(responde?.content?.memberId!!,etAlamat.text.toString(),etKota.text.toString(),listProvinsi.get(spProvinsi.selectedItemPosition).provinceName!!,etKodePos.text.toString())
            }

        }

        responde?.content?.pekerjaan?.toInt()?.let { spPekerjaan.setSelection(it) }

        responde?.content?.pendidikan?.toInt()?.let { spPendidikan.setSelection(it) }


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
            etNama.requestFocus()
            return  false
        }

        if(etPhone.text.isNullOrEmpty()){
            etPhone.setError( "is required!" )
            etPhone
            return  false
        }

        if(etEmial.text.isNullOrEmpty()){
            etEmial.setError( "is required!" )
            etEmial.requestFocus()
            return  false
        }
        if(spBank.selectedItemPosition ==0){
            showError("Bank Belum Dipilih")
            return  false
        }

        if(etNoRek.text.isNullOrEmpty()){
            etNoRek.setError( "is required!" )
            etNoRek.requestFocus()
            return  false
        }


        if(etNik.text.isNullOrEmpty()){
            etNik.setError( "is required!" )
            etNik.requestFocus()
            return  false
        }


        if(etTanggalLahir.text.isNullOrEmpty()){
            etTanggalLahir.setError( "is required!" )
            etTanggalLahir.requestFocus()
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
