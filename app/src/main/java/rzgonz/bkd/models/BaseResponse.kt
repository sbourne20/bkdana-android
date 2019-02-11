package rzgonz.bkd.models

import com.google.gson.annotations.SerializedName

class BaseResponse<T>(

	@field:SerializedName("response")
	val response: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null,

	@field:SerializedName("content")
	val content: T? = null
)