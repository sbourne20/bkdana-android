package rzgonz.bkd.models.peminjam.detail

import com.google.gson.annotations.SerializedName
import rzgonz.bkd.models.peminjam.ListPeminjamItem

data class PeminjamDetailResponse(

		@field:SerializedName("response")
	val response: String? = null,

		@field:SerializedName("content")
	val content: ListPeminjamItem? = null,

		@field:SerializedName("status")
	val status: Int? = null
)