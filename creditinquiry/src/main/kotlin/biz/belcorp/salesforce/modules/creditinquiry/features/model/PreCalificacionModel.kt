package biz.belcorp.salesforce.modules.creditinquiry.features.model

import android.os.Parcel
import android.os.Parcelable

class PreCalificacionModel() : Parcelable {

    var idSujeto: Int = -1
    var nombres: String? = null
    var dui: String? = null
    var nit: String? = null
    var iva: String? = null
    var precalificalificacion: String? = null
    var codigoAprobacion: String? = null
    var credPeorEstadoDescrip: String? = null
    var razonRechazo: String? = null
    var acreedores: String? = null
    var mensaje: String? = null

    constructor(parcel: Parcel) : this() {
        idSujeto = parcel.readInt()
        nombres = parcel.readString()
        dui = parcel.readString()
        nit = parcel.readString()
        iva = parcel.readString()
        precalificalificacion = parcel.readString()
        codigoAprobacion = parcel.readString()
        credPeorEstadoDescrip = parcel.readString()
        razonRechazo = parcel.readString()
        acreedores = parcel.readString()
        mensaje = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(idSujeto)
        parcel.writeString(nombres)
        parcel.writeString(dui)
        parcel.writeString(nit)
        parcel.writeString(iva)
        parcel.writeString(precalificalificacion)
        parcel.writeString(codigoAprobacion)
        parcel.writeString(credPeorEstadoDescrip)
        parcel.writeString(razonRechazo)
        parcel.writeString(acreedores)
        parcel.writeString(mensaje)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PreCalificacionModel> {
        override fun createFromParcel(parcel: Parcel): PreCalificacionModel {
            return PreCalificacionModel(parcel)
        }

        override fun newArray(size: Int): Array<PreCalificacionModel?> {
            return arrayOfNulls(size)
        }
    }
}
