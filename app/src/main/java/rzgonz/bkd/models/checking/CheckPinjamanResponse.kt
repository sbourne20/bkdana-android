package rzgonz.bkd.models.checking

import com.google.gson.annotations.SerializedName

data class CheckPinjamanResponse(

	@field:SerializedName("response")
	val response: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)