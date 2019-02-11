package rzgonz.bkd.models.provinsi

import com.google.gson.annotations.SerializedName

data class ProvinsiItem(

	@field:SerializedName("province_name")
	val provinceName: String? = null
)