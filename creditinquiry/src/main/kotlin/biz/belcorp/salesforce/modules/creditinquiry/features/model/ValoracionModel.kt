package biz.belcorp.salesforce.modules.creditinquiry.features.model

import android.os.Parcel
import android.os.Parcelable

class ValoracionModel : Parcelable {

    var tipo: String? = null
    var valor: String? = null

    constructor()

    constructor(parcel: Parcel) {
        valor = parcel.readString()
        tipo = parcel.readString()
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(parcel: Parcel, i: Int) {
        parcel.writeString(valor)
        parcel.writeString(tipo)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ValoracionModel> =
            object : Parcelable.Creator<ValoracionModel> {
                override fun createFromParcel(parcel: Parcel): ValoracionModel {
                    return ValoracionModel(parcel)
                }

                override fun newArray(size: Int): Array<ValoracionModel?> {
                    return arrayOfNulls(size)
                }
            }
    }

}
