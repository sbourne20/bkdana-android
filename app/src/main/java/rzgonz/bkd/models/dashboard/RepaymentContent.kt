package rzgonz.bkd.models.dashboard

import com.google.gson.annotations.SerializedName

data class RepaymentContent(

	@field:SerializedName("tipe_user")
	val tipeUser: String? = null,

	@field:SerializedName("list_repayment")
	val listRepayment: List<ListRepaymentItem?>? = null,

	@field:SerializedName("saldo")
	val saldo: String? = null,

	@field:SerializedName("jml_all_transaksi")
	val jmlAllTransaksi: String? = null
)