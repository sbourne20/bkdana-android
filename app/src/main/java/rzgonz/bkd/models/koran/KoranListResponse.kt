package rzgonz.bkd.models.koran

import com.google.gson.annotations.SerializedName

data class KoranListResponse(

		@field:SerializedName("response")
	val response: String? = null,

		@field:SerializedName("content")
	val content: List<KorantItem?>? = null,

		@field:SerializedName("status")
	val status: Int? = null
)