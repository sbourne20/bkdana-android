package rzgonz.bkd.models.dashboard

import com.google.gson.annotations.SerializedName

data class MySaldoResponse(

		@field:SerializedName("response")
	val response: String? = null,

		@field:SerializedName("content")
	val content: SaldoContent? = null,

		@field:SerializedName("status")
	val status: Int? = null
)