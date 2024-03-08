package biz.belcorp.salesforce.modules.consultants.features.list.models

import android.os.Parcel
import android.os.Parcelable
import biz.belcorp.salesforce.core.constants.Constant

class ConsultantDetailListSetUpModel(
    val id: Int,
    val title: String? = Constant.EMPTY_STRING,
    val description: String? = Constant.EMPTY_STRING,
    var consultorasNo: String? = Constant.EMPTY_STRING
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(consultorasNo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ConsultantDetailListSetUpModel> {
        override fun createFromParcel(parcel: Parcel): ConsultantDetailListSetUpModel {
            return ConsultantDetailListSetUpModel(parcel)
        }

        override fun newArray(size: Int): Array<ConsultantDetailListSetUpModel?> {
            return arrayOfNulls(size)
        }
    }
}
