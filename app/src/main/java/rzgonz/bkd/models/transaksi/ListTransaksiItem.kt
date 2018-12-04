package rzgonz.bkd.models.transaksi

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class ListTransaksiItem(

	@field:SerializedName("tgl_approve")
	val tglApprove: String? = null,

	@field:SerializedName("tgl_transaksi")
	val tglTransaksi: String? = null,

	@field:SerializedName("transaksi_id")
	val transaksiId: String? = null,

	@field:SerializedName("total_approve")
	val totalApprove: String? = null,

	@field:SerializedName("date_close")
	val dateClose: String? = null,

	@field:SerializedName("type_business_name")
	val typeBusinessName: String? = null,

	@field:SerializedName("transaksi_status")
	val transaksiStatus: String? = null,

	@field:SerializedName("id_mod_type_business")
	val idModTypeBusiness: String? = null,

	@field:SerializedName("product_title")
	val productTitle: String? = null,

	@field:SerializedName("Loan_term")
	val loanTerm: String? = null,

	@field:SerializedName("totalrp")
	val totalrp: String? = null
) : Parcelable {
	constructor(parcel: Parcel) : this(
			parcel.readString(),
			parcel.readString(),
			parcel.readString(),
			parcel.readString(),
			parcel.readString(),
			parcel.readString(),
			parcel.readString(),
			parcel.readString(),
			parcel.readString(),
			parcel.readString(),
			parcel.readString()) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(tglApprove)
		parcel.writeString(tglTransaksi)
		parcel.writeString(transaksiId)
		parcel.writeString(totalApprove)
		parcel.writeString(dateClose)
		parcel.writeString(typeBusinessName)
		parcel.writeString(transaksiStatus)
		parcel.writeString(idModTypeBusiness)
		parcel.writeString(productTitle)
		parcel.writeString(loanTerm)
		parcel.writeString(totalrp)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<ListTransaksiItem> {
		override fun createFromParcel(parcel: Parcel): ListTransaksiItem {
			return ListTransaksiItem(parcel)
		}

		override fun newArray(size: Int): Array<ListTransaksiItem?> {
			return arrayOfNulls(size)
		}
	}
}