package rzgonz.bkd.models.bank

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class BankItem(

	@field:SerializedName("bank_id")
	val bankId: String? = null,

	@field:SerializedName("bank_name")
	val bankName: String? = null
) : Parcelable {
	constructor(parcel: Parcel) : this(
			parcel.readString(),
			parcel.readString()) {
	}

	override fun writeToParcel(parcel: Parcel, flags: Int) {
		parcel.writeString(bankId)
		parcel.writeString(bankName)
	}

	override fun describeContents(): Int {
		return 0
	}

	companion object CREATOR : Parcelable.Creator<BankItem> {
		override fun createFromParcel(parcel: Parcel): BankItem {
			return BankItem(parcel)
		}

		override fun newArray(size: Int): Array<BankItem?> {
			return arrayOfNulls(size)
		}
	}
}