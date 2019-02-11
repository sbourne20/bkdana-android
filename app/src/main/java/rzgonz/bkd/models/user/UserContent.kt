package rzgonz.bkd.models.user

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class UserContent(

		@field:SerializedName("member_id")
		var member_id: String? = null,

	@field:SerializedName("Nomor_nik")
	var nomorNik: String? = null,

	@field:SerializedName("Alamat")
	var alamat: String? = null,

	@field:SerializedName("Kodepos")
	var kodepos: String? = null,

	@field:SerializedName("Pekerjaan")
	var pekerjaan: String? = null,

	@field:SerializedName("nama_perusahaan")
	var namaPerusahaan: String? = null,

	@field:SerializedName("Nomor_rekening")
	var nomorRekening: String? = null,

	@field:SerializedName("Kota")
	var kota: String? = null,

	@field:SerializedName("lama_bekerja")
	var lamaBekerja: String = "0",

	@field:SerializedName("Pendidikan")
	var pendidikan: Int? = null,

	@field:SerializedName("Nama_pengguna")
	var namaPengguna: String? = null,

	@field:SerializedName("Jenis_kelamin")
	var jenisKelamin: String? = null,

	@field:SerializedName("Provinsi")
	var provinsi: String? = null,

	@field:SerializedName("Tempat_lahir")
	var tempatLahir: String? = null,

	@field:SerializedName("Tanggal_lahir")
	var tanggalLahir: String? = null,

	@field:SerializedName("no_telp_perusahaan")
	var noTelpPerusahaan: String? = null,

	@field:SerializedName("deskripsi_usaha")
	var deskripsi_usaha: String? = null,

	@field:SerializedName("omzet_usaha")
	var omzet_usaha: String? = null,

	@field:SerializedName("margin_usaha")
	var margin_usaha: String? = null,
	@field:SerializedName("biaya_operasional_usaha")
	var biaya_operasional_usaha: String? = null,
	@field:SerializedName("laba_usaha")
	var laba_usaha: String? = null,
	@field:SerializedName("jml_bunga_usaha")
	var jml_bunga_usaha: String? = null,
	@field:SerializedName("lama_usaha")
	var lama_usaha: String? = null

) : Parcelable {
	constructor(parcel: Parcel) : this(
			parcel.readString(),
			parcel.readString(),
			parcel.readString(),
			parcel.readString(),
			parcel.readString(),
			parcel.readString(),
			parcel.readString(),
			parcel.readString(),
			parcel.readString(),
			parcel.readValue(Int::class.java.classLoader) as? Int,
			parcel.readString(),
			parcel.readString(),
			parcel.readString(),
			parcel.readString(),
			parcel.readString(),
			parcel.readString(),
			parcel.readString(),
			parcel.readString(),
			parcel.readString(),
			parcel.readString(),
			parcel.readString(),
			parcel.readString(),
			parcel.readString()) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(member_id)
		parcel.writeString(nomorNik)
		parcel.writeString(alamat)
		parcel.writeString(kodepos)
		parcel.writeString(pekerjaan)
		parcel.writeString(namaPerusahaan)
		parcel.writeString(nomorRekening)
		parcel.writeString(kota)
		parcel.writeString(lamaBekerja)
		parcel.writeValue(pendidikan)
		parcel.writeString(namaPengguna)
		parcel.writeString(jenisKelamin)
		parcel.writeString(provinsi)
		parcel.writeString(tempatLahir)
		parcel.writeString(tanggalLahir)
		parcel.writeString(noTelpPerusahaan)
		parcel.writeString(deskripsi_usaha)
		parcel.writeString(omzet_usaha)
		parcel.writeString(margin_usaha)
		parcel.writeString(biaya_operasional_usaha)
		parcel.writeString(laba_usaha)
		parcel.writeString(jml_bunga_usaha)
		parcel.writeString(lama_usaha)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<UserContent> {
		override fun createFromParcel(parcel: Parcel): UserContent {
			return UserContent(parcel)
		}

		override fun newArray(size: Int): Array<UserContent?> {
			return arrayOfNulls(size)
		}
	}
}