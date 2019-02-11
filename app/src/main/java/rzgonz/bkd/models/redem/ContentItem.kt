package rzgonz.bkd.models.redem

import com.google.gson.annotations.SerializedName

data class ContentItem(

	@field:SerializedName("redeem_nama_bank")
	val redeemNamaBank: String? = null,

	@field:SerializedName("mod_redeem_id")
	val modRedeemId: String? = null,

	@field:SerializedName("redeem_amount")
	val redeemAmount: String? = null,

	@field:SerializedName("redeem_nomor_rekening")
	val redeemNomorRekening: String? = null,

	@field:SerializedName("redeem_date")
	val redeemDate: String? = null,

	@field:SerializedName("redeem_status")
	val redeemStatus: String? = null,

	@field:SerializedName("redeem_id_pengguna")
	val redeemIdPengguna: String? = null,

	@field:SerializedName("redeem_kode")
	val redeemKode: String? = null,

	@field:SerializedName("redeem_status_date")
	val redeemStatusDate: String? = null,

	@field:SerializedName("redeem_member_id")
	val redeemMemberId: String? = null
)