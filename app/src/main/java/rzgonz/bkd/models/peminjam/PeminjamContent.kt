package rzgonz.bkd.models.peminjam


import com.google.gson.annotations.SerializedName

data class PeminjamContent(

	@field:SerializedName("list_peminjam")
	val listPeminjam: List<ListPeminjamItem?>? = null
)