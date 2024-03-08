package biz.belcorp.salesforce.modules.creditinquiry.features.model

import android.os.Parcel
import android.os.Parcelable

class MotivoModel() : Parcelable {
    var descripcion: String? = null

    constructor(parcel: Parcel) : this() {
        descripcion = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(descripcion)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MotivoModel> {
        override fun createFromParcel(parcel: Parcel): MotivoModel {
            return MotivoModel(parcel)
        }

        override fun newArray(size: Int): Array<MotivoModel?> {
            return arrayOfNulls(size)
        }
    }
}
