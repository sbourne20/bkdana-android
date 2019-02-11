package rzgonz.bkd.models.peminjam

import com.google.gson.annotations.SerializedName

data class PeminjamListResponse(

		@field:SerializedName("response")
	val response: String? = null,

		@field:SerializedName("content")
	val content: PeminjamContent? = null,

		@field:SerializedName("status")
	val status: Int? = null
)