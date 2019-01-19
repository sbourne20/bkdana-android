package rzgonz.bkd.models.pinjaman

import com.google.gson.annotations.SerializedName

data class Content(

	@field:SerializedName("pinjaman")
	val pinjaman: List<PinjamanItem?>? = null,

	@field:SerializedName("products")
	val products: List<ProductsItem?>? = null
)