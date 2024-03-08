package biz.belcorp.salesforce.modules.creditinquiry.features.model

import android.os.Parcel
import android.os.Parcelable

class ConsultarDCResultModel() : Parcelable {

    var estado: String? = null
    var nomEstado: String? = null
    var nombre: String? = null
    var numDocumento: String? = null
    var resultado: String? = null

    constructor(parcel: Parcel) : this() {
        estado = parcel.readString()
        nomEstado = parcel.readString()
        nombre = parcel.readString()
        numDocumento = parcel.readString()
        resultado = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(estado)
        parcel.writeString(nomEstado)
        parcel.writeString(nombre)
        parcel.writeString(numDocumento)
        parcel.writeString(resultado)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ConsultarDCResultModel> {
        override fun createFromParcel(parcel: Parcel): ConsultarDCResultModel {
            return ConsultarDCResultModel(parcel)
        }

        override fun newArray(size: Int): Array<ConsultarDCResultModel?> {
            return arrayOfNulls(size)
        }
    }
}
