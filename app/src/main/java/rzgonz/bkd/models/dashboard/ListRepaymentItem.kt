package rzgonz.bkd.models.dashboard

import com.google.gson.annotations.SerializedName
data class ListRepaymentItem(

	@field:SerializedName("nominal_transaksi")
	val nominalTransaksi: String? = null,

	@field:SerializedName("no_transaksi")
	val noTransaksi: String? = null,

	@field:SerializedName("jatuh_tempo_transaksi")
	val jatuhTempoTransaksi: String? = null,

	@field:SerializedName("title_transaksi")
	val titleTransaksi: String? = null
)