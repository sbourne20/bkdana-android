package rzgonz.bkd.models.pinjaman

import com.google.gson.annotations.SerializedName

data class PinjamanItem(

	@field:SerializedName("nominal_pinjaman")
	val nominalPinjaman: String? = null,

	@field:SerializedName("id")
	val id: String? = null
)