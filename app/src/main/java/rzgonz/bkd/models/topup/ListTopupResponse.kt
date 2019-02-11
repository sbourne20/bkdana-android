package rzgonz.bkd.models.topup

import com.google.gson.annotations.SerializedName

data class ListTopupResponse(

	@field:SerializedName("response")
	val response: String? = null,

	@field:SerializedName("content")
	val content: List<ContentItem?>? = null,

	@field:SerializedName("status")
	val status: Int? = null
)