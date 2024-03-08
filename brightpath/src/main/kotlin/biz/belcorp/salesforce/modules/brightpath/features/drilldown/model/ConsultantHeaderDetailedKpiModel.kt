package biz.belcorp.salesforce.modules.brightpath.features.drilldown.model

import android.os.Parcel
import android.os.Parcelable
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING

class ConsultantHeaderDetailedKpiModel(
        val id: Int,
        val title: String? = EMPTY_STRING,
        val description: String? = EMPTY_STRING,
        var consultantQuantity: String? = EMPTY_STRING)
    : Parcelable {

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString())

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(consultantQuantity)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ConsultantHeaderDetailedKpiModel> {
        override fun createFromParcel(parcel: Parcel): ConsultantHeaderDetailedKpiModel {
            return ConsultantHeaderDetailedKpiModel(parcel)
        }

        override fun newArray(size: Int): Array<ConsultantHeaderDetailedKpiModel?> {
            return arrayOfNulls(size)
        }
    }
}
