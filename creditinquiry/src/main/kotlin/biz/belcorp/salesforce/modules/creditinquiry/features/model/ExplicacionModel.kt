package biz.belcorp.salesforce.modules.creditinquiry.features.model

import android.os.Parcel
import android.os.Parcelable

class ExplicacionModel : Parcelable {

    var valor: String? = null

    constructor()

    constructor(parcel: Parcel) {
        valor = parcel.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeString(valor)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ExplicacionModel> =
            object : Parcelable.Creator<ExplicacionModel> {
                override fun createFromParcel(parcel: Parcel): ExplicacionModel {
                    return ExplicacionModel(parcel)
                }

                override fun newArray(size: Int): Array<ExplicacionModel?> {
                    return arrayOfNulls(size)
                }
            }
    }

}
