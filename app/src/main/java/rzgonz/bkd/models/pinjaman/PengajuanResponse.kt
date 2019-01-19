package rzgonz.bkd.models.pinjaman

import com.google.gson.annotations.SerializedName

data class PengajuanResponse(

	@field:SerializedName("response")
	val response: String? = null,

	@field:SerializedName("content")
	val content: Content? = null,

	@field:SerializedName("status")
	val status: Int? = null
)