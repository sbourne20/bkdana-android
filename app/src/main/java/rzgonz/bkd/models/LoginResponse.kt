package rzgonz.bkd.models

import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("response")
	var response: String = "",

	@field:SerializedName("name")
	var name: String = "",

	@field:SerializedName("status")
	var status: Int =500,

	@field:SerializedName("token")
	var token: String = "",

	@field:SerializedName("logtype")
	var logtype: String = "",

	@field:SerializedName("tpeminjam")
	var typePeminjam: String = ""
)