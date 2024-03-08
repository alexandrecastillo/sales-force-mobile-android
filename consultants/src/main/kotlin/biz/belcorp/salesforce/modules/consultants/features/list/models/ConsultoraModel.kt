package biz.belcorp.salesforce.modules.consultants.features.list.models

import android.os.Parcel
import android.os.Parcelable
import biz.belcorp.salesforce.modules.consultants.core.domain.entities.Consultora

class ConsultoraModel() : Parcelable {

    var consultorasId: Int = 0

    var seccion: String? = null

    var nombre: String? = null

    var codigo: String? = null

    var telefonoCelular: String? = null

    var constancia: String? = null

    var segmento: String? = null

    var saldoPendiente: String? = null

    var primerNombre: String? = null

    var segundoNombre: String? = null

    var primerApellido: String? = null

    var segundoApellido: String? = null

    var latitud: Double = 0.0

    var longitud: Double = 0.0

    var nivel: String? = null

    var digitoVerificador: String? = null

    var monto: String? = null

    var nivelActual: String? = null

    var nivelSiguiente: String? = null

    constructor(parcel: Parcel) : this() {
        consultorasId = parcel.readInt()
        seccion = parcel.readString()
        nombre = parcel.readString()
        codigo = parcel.readString()
        telefonoCelular = parcel.readString()
        constancia = parcel.readString()
        segmento = parcel.readString()
        saldoPendiente = parcel.readString()
        primerNombre = parcel.readString()
        segundoNombre = parcel.readString()
        primerApellido = parcel.readString()
        segundoApellido = parcel.readString()
        latitud = parcel.readDouble()
        longitud = parcel.readDouble()
        nivel = parcel.readString()
        digitoVerificador = parcel.readString()
        monto = parcel.readString()
        nivelActual = parcel.readString()
        nivelSiguiente = parcel.readString()
    }

    fun parse(entity: Consultora): ConsultoraModel {
        this.consultorasId = entity.consultorasId
        this.seccion = entity.seccion
        this.nombre = entity.nombre
        this.codigo = entity.codigo
        this.telefonoCelular = entity.telefonoCelular
        this.constancia = entity.constancia
        this.segmento = entity.segmento
        this.saldoPendiente = entity.saldoPendiente
        this.primerNombre = entity.primerNombre
        this.segundoNombre = entity.segundoNombre
        this.primerApellido = entity.primerApellido
        this.segundoApellido = entity.segundoApellido
        this.latitud = isNull(entity.latitud)
        this.longitud = isNull(entity.longitud)
        this.nivel = entity.nivel
        this.digitoVerificador = entity.digitoVerificador

        this.monto = entity.monto
        this.nivelActual = entity.nivelActual
        this.nivelSiguiente = entity.nivelSiguiente

        return this
    }

    private fun isNull(mDouble: Double?): Double {
        if (mDouble == null) {
            return 0.0
        }
        return mDouble
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(consultorasId)
        parcel.writeString(seccion)
        parcel.writeString(nombre)
        parcel.writeString(codigo)
        parcel.writeString(telefonoCelular)
        parcel.writeString(constancia)
        parcel.writeString(segmento)
        parcel.writeString(saldoPendiente)
        parcel.writeString(primerNombre)
        parcel.writeString(segundoNombre)
        parcel.writeString(primerApellido)
        parcel.writeString(segundoApellido)
        parcel.writeDouble(latitud)
        parcel.writeDouble(longitud)
        parcel.writeString(nivel)
        parcel.writeString(digitoVerificador)
        parcel.writeString(monto)
        parcel.writeString(nivelActual)
        parcel.writeString(nivelSiguiente)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ConsultoraModel> {
        override fun createFromParcel(parcel: Parcel): ConsultoraModel {
            return ConsultoraModel(parcel)
        }

        override fun newArray(size: Int): Array<ConsultoraModel?> {
            return arrayOfNulls(size)
        }
    }

}
