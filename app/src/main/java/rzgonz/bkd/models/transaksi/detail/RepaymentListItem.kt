package rzgonz.bkd.models.transaksi.detail


import com.google.gson.annotations.SerializedName
data class RepaymentListItem(

	@field:SerializedName("nominal_cicilan")
	val nominalCicilan: String? = null,

	@field:SerializedName("jatuh_tempo")
	val jatuhTempo: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)