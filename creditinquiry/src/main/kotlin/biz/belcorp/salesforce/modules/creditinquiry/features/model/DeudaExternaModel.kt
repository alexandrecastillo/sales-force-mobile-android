package biz.belcorp.salesforce.modules.creditinquiry.features.model

import android.os.Parcel
import android.os.Parcelable

class DeudaExternaModel : Parcelable {
    var entidadCrediticia: String? = null

    var monto: String? = null

    var simboloMoneda: String? = null

    constructor()

    constructor(parcel: Parcel) {
        entidadCrediticia = parcel.readString()
        simboloMoneda = parcel.readString()
        monto = parcel.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeString(entidadCrediticia)
        parcel.writeString(simboloMoneda)
        parcel.writeString(monto)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<DeudaExternaModel> =
            object : Parcelable.Creator<DeudaExternaModel> {
                override fun createFromParcel(parcel: Parcel): DeudaExternaModel {
                    return DeudaExternaModel(parcel)
                }

                override fun newArray(size: Int): Array<DeudaExternaModel?> {
                    return arrayOfNulls(size)
                }
            }
    }
}
