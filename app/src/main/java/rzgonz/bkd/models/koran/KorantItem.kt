package rzgonz.bkd.models.koran

import com.google.gson.annotations.SerializedName

data class KorantItem(

	@field:SerializedName("kode_transaksi")
	val kodeTransaksi: String? = null,

	@field:SerializedName("Detail_wallet_id")
	val detailWalletId: String? = null,

	@field:SerializedName("amount_detail")
	val amountDetail: String? = null,

	@field:SerializedName("wallet_member_id")
	val walletMemberId: String? = null,

	@field:SerializedName("Id")
	val id: String? = null,

	@field:SerializedName("User_id")
	val userId: String? = null,

	@field:SerializedName("Date_transaction")
	val dateTransaction: String? = null,

	@field:SerializedName("Notes")
	val notes: String? = null,

	@field:SerializedName("tipe_dana")
	val tipeDana: String? = null
)