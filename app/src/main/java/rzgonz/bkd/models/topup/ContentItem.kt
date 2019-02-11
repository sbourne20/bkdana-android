package rzgonz.bkd.models.topup

import com.google.gson.annotations.SerializedName

data class ContentItem(

	@field:SerializedName("jml_top_up")
	val jmlTopUp: String? = null,

	@field:SerializedName("status_top_up")
	val statusTopUp: String? = null,

	@field:SerializedName("tgl_top_up")
	val tglTopUp: String? = null,

	@field:SerializedName("kode_top_up")
	val kodeTopUp: String? = null
)