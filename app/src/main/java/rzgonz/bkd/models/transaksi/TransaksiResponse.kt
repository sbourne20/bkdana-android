package rzgonz.bkd.models.transaksi

import com.google.gson.annotations.SerializedName

data class TransaksiResponse(

        @field:SerializedName("response")
	val response: String? = null,

        @field:SerializedName("list_transaksi")
	val listTransaksi: List<ListTransaksiItem?>? = null,

        @field:SerializedName("status")
	val status: Int? = null,

        @field:SerializedName("token")
	val token: String? = null
)