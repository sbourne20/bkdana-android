package rzgonz.bkd.models.user

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class UserContent(
		@field:SerializedName("Nomor_nik")
		var nomorNik: String? = null,

		@field:SerializedName("Mobileno")
		var mobileno: String? = null,

		@field:SerializedName("Pekerjaan")
		var pekerjaan: String? = null,

		@field:SerializedName("jml_bunga_usaha")
		var jmlBungaUsaha: String? = null,

		@field:SerializedName("nama_perusahaan")
		var namaPerusahaan: String? = null,

		@field:SerializedName("biaya_operasional_usaha")
		var biayaOperasionalUsaha: String? = null,

		@field:SerializedName("peringkat_pengguna_persentase")
		var peringkatPenggunaPersentase: String? = null,

		@field:SerializedName("foto_usaha_file")
		var fotoUsahaFile: String? = null,

		@field:SerializedName("foto_usaha_file2")
		var fotoUsahaFile2: String? = null,

		@field:SerializedName("foto_usaha_file3")
		var fotoUsahaFile3: String? = null,

		@field:SerializedName("foto_usaha_file4")
		var fotoUsahaFile4: String? = null,

		@field:SerializedName("foto_usaha_file5")
		var fotoUsahaFile5: String? = null,

		@field:SerializedName("Nomor_rekening")
		var nomorRekening: String? = null,

		@field:SerializedName("lama_usaha")
		var lamaUsaha: String? = null,

		@field:SerializedName("Kota")
		var kota: String? = null,

		@field:SerializedName("skoring")
		var skoring: String? = null,

		@field:SerializedName("foto_surat_ket_kerja")
		var fotoSuratKetKerja: String? = null,

		@field:SerializedName("Nama_pengguna")
		var namaPengguna: String? = null,

		@field:SerializedName("foto_file")
		var fotoFile: String? = null,

		@field:SerializedName("foto_pegang_idcard")
		var fotoPegangIdcard: String? = null,

		@field:SerializedName("Jenis_kelamin")
		var jenisKelamin: String? = null,

		@field:SerializedName("usaha")
		var usaha: String? = null,

		@field:SerializedName("nama_bank")
		var namaBank: String? = null,

		@field:SerializedName("lama_bekerja")
		var lamaBekerja: String? = null,

		@field:SerializedName("email")
		var email: String? = null,

		@field:SerializedName("nama_atasan")
		var namaAtasan: String? = null,

		@field:SerializedName("Tanggal_lahir")
		var tanggalLahir: String? = null,

		@field:SerializedName("no_telp_perusahaan")
		var noTelpPerusahaan: String? = null,

		@field:SerializedName("member_id")
		var memberId: String? = null,

		@field:SerializedName("Alamat")
		var alamat: String? = null,

		@field:SerializedName("Kodepos")
		var kodepos: String? = null,

		@field:SerializedName("deskripsi_usaha")
		var deskripsiUsaha: String? = null,

		@field:SerializedName("gaji")
		var gaji: String? = null,

		@field:SerializedName("laba_usaha")
		var labaUsaha: String? = null,

		@field:SerializedName("omzet_usaha")
		var omzetUsaha: String? = null,

		@field:SerializedName("foto_slip_gaji")
		var fotoSlipGaji: String? = null,

		@field:SerializedName("Pendidikan")
		var pendidikan: String? = null,

		@field:SerializedName("margin_usaha")
		var marginUsaha: String? = null,

		@field:SerializedName("referensi_nama_2")
		var referensiNama2: String? = null,

		@field:SerializedName("peringkat_pengguna")
		var peringkatPengguna: String? = null,

		@field:SerializedName("Provinsi")
		var provinsi: String? = null,

		@field:SerializedName("status_karyawan")
		var statusKaryawan: String? = null,

		@field:SerializedName("referensi_nama_1")
		var referensiNama1: String? = null,

		@field:SerializedName("nik_file")
		var nikFile: String? = null,

		@field:SerializedName("referensi_2")
		var referensi2: String? = null,

		@field:SerializedName("referensi_1")
		var referensi1: String? = null,

		@field:SerializedName("Tempat_lahir")
		var tempatLahir: String? = null
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
			parcel.readString(),
			parcel.readString()) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(nomorNik)
		parcel.writeString(mobileno)
		parcel.writeString(pekerjaan)
		parcel.writeString(jmlBungaUsaha)
		parcel.writeString(namaPerusahaan)
		parcel.writeString(biayaOperasionalUsaha)
		parcel.writeString(peringkatPenggunaPersentase)
		parcel.writeString(fotoUsahaFile)
		parcel.writeString(fotoUsahaFile2)
		parcel.writeString(fotoUsahaFile3)
		parcel.writeString(fotoUsahaFile4)
		parcel.writeString(fotoUsahaFile5)
		parcel.writeString(nomorRekening)
		parcel.writeString(lamaUsaha)
		parcel.writeString(kota)
		parcel.writeString(skoring)
		parcel.writeString(fotoSuratKetKerja)
		parcel.writeString(namaPengguna)
		parcel.writeString(fotoFile)
		parcel.writeString(fotoPegangIdcard)
		parcel.writeString(jenisKelamin)
		parcel.writeString(usaha)
		parcel.writeString(namaBank)
		parcel.writeString(lamaBekerja)
		parcel.writeString(email)
		parcel.writeString(namaAtasan)
		parcel.writeString(tanggalLahir)
		parcel.writeString(noTelpPerusahaan)
		parcel.writeString(memberId)
		parcel.writeString(alamat)
		parcel.writeString(kodepos)
		parcel.writeString(deskripsiUsaha)
		parcel.writeString(gaji)
		parcel.writeString(labaUsaha)
		parcel.writeString(omzetUsaha)
		parcel.writeString(fotoSlipGaji)
		parcel.writeString(pendidikan)
		parcel.writeString(marginUsaha)
		parcel.writeString(referensiNama2)
		parcel.writeString(peringkatPengguna)
		parcel.writeString(provinsi)
		parcel.writeString(statusKaryawan)
		parcel.writeString(referensiNama1)
		parcel.writeString(nikFile)
		parcel.writeString(referensi2)
		parcel.writeString(referensi1)
		parcel.writeString(tempatLahir)
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
