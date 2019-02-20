package rzgonz.bkd.models.checking

import com.google.gson.annotations.SerializedName

data class Content(

	@field:SerializedName("products")
	val products: List<ProductsItem?>? = null
)