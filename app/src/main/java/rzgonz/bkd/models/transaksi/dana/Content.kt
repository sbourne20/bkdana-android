package rzgonz.bkd.models.transaksi.dana

import com.google.gson.annotations.SerializedName
import rzgonz.bkd.models.transaksi.ListTransaksiItem

data class Content(

	@field:SerializedName("list_transaksi")
	val listTransaksi: List<ListTransaksiItem>? = null
)