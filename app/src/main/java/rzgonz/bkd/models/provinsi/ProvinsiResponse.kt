package rzgonz.bkd.models.provinsi

import com.google.gson.annotations.SerializedName

data class ProvinsiResponse(

        @field:SerializedName("response")
	val response: String? = null,

        @field:SerializedName("content")
	val content: List<ProvinsiItem?>? = null,

        @field:SerializedName("status")
	val status: Int? = null
)