package rzgonz.bkd.models.user

import com.google.gson.annotations.SerializedName
data class UserResponse(

	@field:SerializedName("response")
	var response: String? = null,

	@field:SerializedName("content")
	var content: Content? = null,

	@field:SerializedName("status")
	var status: Int? = null
)