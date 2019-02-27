package rzgonz.bkd.models

import com.google.gson.annotations.SerializedName

class BaseResponseMessage(

	@field:SerializedName("response")
	val response: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null

)