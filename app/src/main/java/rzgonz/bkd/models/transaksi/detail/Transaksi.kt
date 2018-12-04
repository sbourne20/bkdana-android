package rzgonz.bkd.models.transaksi.detail

import com.google.gson.annotations.SerializedName
data class Transaksi(

	@field:SerializedName("date_fundraise")
	val dateFundraise: String? = null,

	@field:SerializedName("Pekerjaan")
	val pekerjaan: String? = null,

	@field:SerializedName("transaksi_id")
	val transaksiId: String? = null,

	@field:SerializedName("size_foto_usaha")
	val sizeFotoUsaha: String? = null,

	@field:SerializedName("Product_id")
	val productId: String? = null,

	@field:SerializedName("fundraising_period")
	val fundraisingPeriod: String? = null,

	@field:SerializedName("foto_usaha")
	val fotoUsaha: String? = null,

	@field:SerializedName("id_mod_user_member")
	val idModUserMember: String? = null,

	@field:SerializedName("Kota")
	val kota: Any? = null,

	@field:SerializedName("tgl_pinjaman_disetujui")
	val tglPinjamanDisetujui: String? = null,

	@field:SerializedName("Master_loan_status")
	val masterLoanStatus: String? = null,

	@field:SerializedName("Total_loan_outstanding")
	val totalLoanOutstanding: String? = null,

	@field:SerializedName("Total_loan_repayment")
	val totalLoanRepayment: String? = null,

	@field:SerializedName("What_is_the_name_of_your_business")
	val whatIsTheNameOfYourBusiness: String? = null,

	@field:SerializedName("How_many_years_have_you_been_in_business")
	val howManyYearsHaveYouBeenInBusiness: String? = null,

	@field:SerializedName("Nama_pengguna")
	val namaPengguna: String? = null,

	@field:SerializedName("date_close")
	val dateClose: String? = null,

	@field:SerializedName("Investor_return")
	val investorReturn: String? = null,

	@field:SerializedName("jml_kredit")
	val jmlKredit: String? = null,

	@field:SerializedName("Alamat")
	val alamat: Any? = null,

	@field:SerializedName("size_foto_profil")
	val sizeFotoProfil: Any? = null,

	@field:SerializedName("Id_pengguna")
	val idPengguna: String? = null,

	@field:SerializedName("Profile_photo")
	val profilePhoto: Any? = null,

	@field:SerializedName("Master_loan_id")
	val masterLoanId: String? = null,

	@field:SerializedName("total_lender")
	val totalLender: String? = null,

	@field:SerializedName("Jml_permohonan_pinjaman_disetujui")
	val jmlPermohonanPinjamanDisetujui: String? = null,

	@field:SerializedName("Loan_term")
	val loanTerm: String? = null,

	@field:SerializedName("Informasi_kredit")
	val informasiKredit: String? = null,

	@field:SerializedName("type_of_business_id")
	val typeOfBusinessId: String? = null,

	@field:SerializedName("images_usaha_name")
	val imagesUsahaName: String? = null,

	@field:SerializedName("peringkat_pengguna")
	val peringkatPengguna: String? = null,

	@field:SerializedName("images_foto_name")
	val imagesFotoName: String? = null,

	@field:SerializedName("type_business_name")
	val typeBusinessName: String? = null,

	@field:SerializedName("Provinsi")
	val provinsi: Any? = null,

	@field:SerializedName("nama_peminjam")
	val namaPeminjam: String? = null,

	@field:SerializedName("Jml_permohonan_pinjaman")
	val jmlPermohonanPinjaman: String? = null
)