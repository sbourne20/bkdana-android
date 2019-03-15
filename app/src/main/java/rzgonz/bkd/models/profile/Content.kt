package rzgonz.bkd.models.profile

import com.google.gson.annotations.SerializedName

data class Content(

	@field:SerializedName("Mobileno")
	val mobileno: String? = null,

	@field:SerializedName("mum_status")
	val mumStatus: String? = null,

	@field:SerializedName("mum_password")
	val mumPassword: String? = null,

	@field:SerializedName("peringkat_pengguna_persentase")
	val peringkatPenggunaPersentase: String? = null,

	@field:SerializedName("Nomor_rekening")
	val nomorRekening: String? = null,

	@field:SerializedName("mum_email")
	val mumEmail: String? = null,

	@field:SerializedName("id_mod_user_member")
	val idModUserMember: String? = null,

	@field:SerializedName("Kota")
	val kota: String? = null,

	@field:SerializedName("mum_usaha")
	val mumUsaha: String? = null,

	@field:SerializedName("Nama_pengguna")
	val namaPengguna: String? = null,

	@field:SerializedName("mum_create_date")
	val mumCreateDate: String? = null,

	@field:SerializedName("mum_lama_usaha")
	val mumLamaUsaha: String? = null,

	@field:SerializedName("nama_bank")
	val namaBank: String? = null,

	@field:SerializedName("Id_ktp")
	val idKtp: String? = null,

	@field:SerializedName("Alamat")
	val alamat: String? = null,

	@field:SerializedName("mum_fullname")
	val mumFullname: String? = null,

	@field:SerializedName("Kodepos")
	val kodepos: String? = null,

	@field:SerializedName("mum_telp")
	val mumTelp: String? = null,

	@field:SerializedName("Id_pengguna")
	val idPengguna: String? = null,

	@field:SerializedName("mum_type_peminjam")
	val mumTypePeminjam: String? = null,

	@field:SerializedName("Profile_photo")
	val profilePhoto: Any? = null,

	@field:SerializedName("mum_nomor_rekening")
	val mumNomorRekening: String? = null,

	@field:SerializedName("mum_ktp")
	val mumKtp: String? = null,

	@field:SerializedName("peringkat_pengguna")
	val peringkatPengguna: String? = null,

	@field:SerializedName("images_foto_name")
	val imagesFotoName: String? = null,

	@field:SerializedName("Provinsi")
	val provinsi: String? = null,
	@field:SerializedName("nik")
	val nik: String? = null,
	@field:SerializedName("tgl_lahir")
	val tgl_lahir: String? = null,
	@field:SerializedName("Jenis_kelamin")
	val Jenis_kelamin: String? = null
)