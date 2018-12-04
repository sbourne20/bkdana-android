package rzgonz.bkd.models.transaksi.detail

import com.google.gson.annotations.SerializedName

data class LogPinjaman(

	@field:SerializedName("ltp_date_created")
	val ltpDateCreated: String? = null,

	@field:SerializedName("ltp_product_title")
	val ltpProductTitle: String? = null,

	@field:SerializedName("ltp_product_loan_term")
	val ltpProductLoanTerm: String? = null,

	@field:SerializedName("ltp_lama_angsuran")
	val ltpLamaAngsuran: String? = null,

	@field:SerializedName("ltp_total_pinjaman_disetujui")
	val ltpTotalPinjamanDisetujui: String? = null,

	@field:SerializedName("ltp_product_loan_organizer")
	val ltpProductLoanOrganizer: String? = null,

	@field:SerializedName("ltp_admin_fee")
	val ltpAdminFee: String? = null,

	@field:SerializedName("ltp_product_secured_loan_fee")
	val ltpProductSecuredLoanFee: String? = null,

	@field:SerializedName("ltp_platform_fee")
	val ltpPlatformFee: String? = null,

	@field:SerializedName("ltp_product_investor_return")
	val ltpProductInvestorReturn: String? = null,

	@field:SerializedName("ltp_product_platform_rate")
	val ltpProductPlatformRate: String? = null,

	@field:SerializedName("ltp_product_id")
	val ltpProductId: String? = null,

	@field:SerializedName("ltp_total_pinjaman")
	val ltpTotalPinjaman: String? = null,

	@field:SerializedName("ltp_type_of_business_id")
	val ltpTypeOfBusinessId: String? = null,

	@field:SerializedName("ltp_lender_fee")
	val ltpLenderFee: String? = null,

	@field:SerializedName("ltp_Master_loan_id")
	val ltpMasterLoanId: String? = null,

	@field:SerializedName("ltp_LO_fee")
	val ltpLOFee: String? = null,

	@field:SerializedName("ltp_bunga_pinjaman")
	val ltpBungaPinjaman: String? = null,

	@field:SerializedName("ltp_jml_angsuran")
	val ltpJmlAngsuran: String? = null,

	@field:SerializedName("ltp_frozen")
	val ltpFrozen: String? = null,

	@field:SerializedName("ltp_product_pph")
	val ltpProductPph: String? = null,

	@field:SerializedName("ltp_id")
	val ltpId: String? = null,

	@field:SerializedName("ltp_product_revenue_share")
	val ltpProductRevenueShare: String? = null,

	@field:SerializedName("ltp_Id_pengguna")
	val ltpIdPengguna: String? = null,

	@field:SerializedName("ltp_product_interest_rate")
	val ltpProductInterestRate: String? = null,

	@field:SerializedName("ltp_product_interest_rate_type")
	val ltpProductInterestRateType: String? = null,

	@field:SerializedName("ltp_loan_organizer_id")
	val ltpLoanOrganizerId: String? = null,

	@field:SerializedName("ltp_tgl_jatuh_tempo")
	val ltpTglJatuhTempo: String? = null
)