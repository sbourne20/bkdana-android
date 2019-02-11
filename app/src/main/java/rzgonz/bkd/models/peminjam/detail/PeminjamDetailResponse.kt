package rzgonz.bkd.models.peminjam.detail

import com.google.gson.annotations.SerializedName

data class PeminjamDetailResponse(

        @field:SerializedName("response")
	val response: String? = null,

        @field:SerializedName("content")
	val content: PeminjamContent? = null,

        @field:SerializedName("status")
	val status: Int? = null
)