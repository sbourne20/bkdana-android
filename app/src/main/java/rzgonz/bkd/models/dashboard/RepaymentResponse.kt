package rzgonz.bkd.models.dashboard

import com.google.gson.annotations.SerializedName
data class RepaymentResponse(

		@field:SerializedName("response")
	val response: String? = null,

		@field:SerializedName("content")
	val content: RepaymentContent? = null,

		@field:SerializedName("status")
	val status: Int? = null
)