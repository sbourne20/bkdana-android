package rzgonz.bkd.models.bank

import com.google.gson.annotations.SerializedName

data class ListBankResponse(

		@field:SerializedName("response")
	val response: String? = null,

		@field:SerializedName("content")
	val content: List<BankItem?>? = null,

		@field:SerializedName("status")
	val status: Int? = null
)