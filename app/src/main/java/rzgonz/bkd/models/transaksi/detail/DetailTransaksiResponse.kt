package rzgonz.bkd.models.transaksi.detail

import com.google.gson.annotations.SerializedName

data class DetailTransaksiResponse(

	@field:SerializedName("response")
	val response: String? = null,

	@field:SerializedName("content")
	val content: Content? = null,

	@field:SerializedName("status")
	val status: Int? = null
)