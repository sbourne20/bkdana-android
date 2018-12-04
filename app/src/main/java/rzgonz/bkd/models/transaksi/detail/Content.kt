package rzgonz.bkd.models.transaksi.detail

import com.google.gson.annotations.SerializedName

data class Content(

	@field:SerializedName("detail_angsuran")
	val detailAngsuran: List<Any?>? = null,

	@field:SerializedName("transaksi")
	val transaksi: Transaksi? = null,

	@field:SerializedName("jatuh_tempo")
	val jatuhTempo: String? = null,

	@field:SerializedName("jml_angsuran")
	val jmlAngsuran: String? = null,

	@field:SerializedName("total_bayar")
	val totalBayar: String? = null,

	@field:SerializedName("log_pinjaman")
	val logPinjaman: LogPinjaman? = null
)