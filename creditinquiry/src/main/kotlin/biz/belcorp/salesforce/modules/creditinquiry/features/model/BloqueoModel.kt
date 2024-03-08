package biz.belcorp.salesforce.modules.creditinquiry.features.model

import android.os.Parcel
import android.os.Parcelable

class BloqueoModel() : Parcelable {
    var motivoBloqueo: String? = null
    var observacion: String? = null
    var tipoBloqueo: String? = null
    var item: Int? = null

    constructor(parcel: Parcel) : this() {
        motivoBloqueo = parcel.readString()
        observacion = parcel.readString()
        tipoBloqueo = parcel.readString()
        item = parcel.readValue(Int::class.java.classLoader) as? Int
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(motivoBloqueo)
        parcel.writeString(observacion)
        parcel.writeString(tipoBloqueo)
        parcel.writeValue(item)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<BloqueoModel> {
        override fun createFromParcel(parcel: Parcel): BloqueoModel {
            return BloqueoModel(parcel)
        }

        override fun newArray(size: Int): Array<BloqueoModel?> {
            return arrayOfNulls(size)
        }
    }
}
