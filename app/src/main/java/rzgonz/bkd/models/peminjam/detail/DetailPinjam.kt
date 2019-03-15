package rzgonz.bkd.models.peminjam.detail

import com.google.gson.annotations.SerializedName

data class DetailPinjam(

	@field:SerializedName("kuota_dana")
	val kuotaDana: String? = null,

	@field:SerializedName("kota")
	val kota: Any? = null,

	@field:SerializedName("total_pinjaman")
	val totalPinjaman: String? = null,

	@field:SerializedName("grade_peminjam")
	val gradePeminjam: String? = null,

	@field:SerializedName("total_lender")
	val totalLender: String? = null,

	@field:SerializedName("lama_usaha")
	val lamaUsaha: String? = null,

	@field:SerializedName("alamat")
	val alamat: Any? = null,

	@field:SerializedName("foto_usaha")
	val fotoUsaha: String? = null,

	@field:SerializedName("tenor")
	val tenor: String? = null,

	@field:SerializedName("foto_profil")
	val fotoProfil: String? = null,

	@field:SerializedName("pekerjaan")
	val pekerjaan: String? = null,

	@field:SerializedName("nama_peminjam")
	val namaPeminjam: String? = null,

	@field:SerializedName("no_transaksi_pinjaman")
	val noTransaksiPinjaman: String? = null,

	@field:SerializedName("total_pendanaan")
	val totalPendanaan: String? = null
)