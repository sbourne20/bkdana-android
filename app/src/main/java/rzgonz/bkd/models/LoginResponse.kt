package rzgonz.bkd.models

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("response")
	val response: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("status")
	val status: Int? = null,

	@field:SerializedName("token")
	val token: String? = null
)