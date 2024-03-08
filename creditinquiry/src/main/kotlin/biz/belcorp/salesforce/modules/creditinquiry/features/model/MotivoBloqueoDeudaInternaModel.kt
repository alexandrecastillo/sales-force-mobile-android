package biz.belcorp.salesforce.modules.creditinquiry.features.model

import android.os.Parcel
import android.os.Parcelable

class MotivoBloqueoDeudaInternaModel : Parcelable {
    var motivoBloqueo: String? = null

    var observacion: String? = null

    var tipoBloqueo: String? = null

    constructor()

    constructor(parcel: Parcel) {
        motivoBloqueo = parcel.readString()
        observacion = parcel.readString()
        tipoBloqueo = parcel.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeString(motivoBloqueo)
        parcel.writeString(observacion)
        parcel.writeString(tipoBloqueo)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<MotivoBloqueoDeudaInternaModel> =
            object : Parcelable.Creator<MotivoBloqueoDeudaInternaModel> {
                override fun createFromParcel(parcel: Parcel): MotivoBloqueoDeudaInternaModel {
                    return MotivoBloqueoDeudaInternaModel(parcel)
                }

                override fun newArray(size: Int): Array<MotivoBloqueoDeudaInternaModel?> {
                    return arrayOfNulls(size)
                }
            }
    }
}
