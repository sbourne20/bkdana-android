package rzgonz.bkd.models.pinjaman

import com.google.gson.annotations.SerializedName

data class ProductsItem(

	@field:SerializedName("Product_id")
	val productId: String? = null,

	@field:SerializedName("Loan_term")
	val loanTerm: String? = null
)