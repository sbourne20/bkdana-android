package rzgonz.bkd.models.transaksi.dana.detail

import com.google.gson.annotations.SerializedName

data class Content(

	@field:SerializedName("tenor")
	val tenor: String? = null,

	@field:SerializedName("jatuh_tempo")
	val jatuhTempo: String? = null,

	@field:SerializedName("status_pendanaan")
	val statusPendanaan: String? = null,

	@field:SerializedName("no_transaksi_pendanaan")
	val noTransaksiPendanaan: String? = null,

	@field:SerializedName("total_pinjaman")
	val totalPinjaman: String? = null,

	@field:SerializedName("total_saldo_diterima")
	val totalSaldoDiterima: String? = null,

	@field:SerializedName("nama_peminjam")
	val namaPeminjam: String? = null,

	@field:SerializedName("status_pinjaman")
	val statusPinjaman: String? = null,

	@field:SerializedName("no_transaksi_pinjaman")
	val noTransaksiPinjaman: String? = null,

	@field:SerializedName("total_pendanaan")
	val totalPendanaan: String? = null
)