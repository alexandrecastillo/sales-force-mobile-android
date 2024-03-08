package biz.belcorp.salesforce.modules.creditinquiry.features.model

import android.os.Parcel
import android.os.Parcelable

class ConsultaCrediticiaInternaModel() : Parcelable {

    var pais: String? = null
    var nombreCompleto: String? = null
    var motivoBloqueo: String? = null
    var tieneBloqueo: Boolean? = null
    var EstadoPostulante: String? = null
    var SubEstadoPostulante: String? = null
    var cumpleValidacionesInternas: String? = null
    var valoracionInterna: String? = null
    var primerApellido: String? = null
    var primerNombre: String? = null
    var segundoApellido: String? = null
    var segundoNombre: String? = null
    var tipoDocumento: String? = null
    var documentoIdentidad: String? = null
    var codigoConsultora: String? = null
    var deudaBelcorp: String? = null
    var deudaEntidadCrediticia: String? = null
    var region: String? = null
    var zona: String? = null
    var seccion: String? = null
    var tipoMoneda: String? = null
    var moneda: String? = null
    var estadoCliente: String? = null
    var campanaIngreso: String? = null
    var ultimaCampanaFacturada: String? = null
    var codestadoCliente: String? = null
    var autorizaPedido: Boolean? = null
    var valoracionBelcorp: String? = null
    var enumEstadoCrediticio: Int? = null
    var mensaje: String? = null
    var valid: Boolean? = null
    var TipoError: String? = null
    var bloqueos: List<BloqueoModel>? = null
    var data: String? = null

    constructor(parcel: Parcel) : this() {
        pais = parcel.readString()
        nombreCompleto = parcel.readString()
        motivoBloqueo = parcel.readString()
        tieneBloqueo = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        EstadoPostulante = parcel.readString()
        SubEstadoPostulante = parcel.readString()
        cumpleValidacionesInternas = parcel.readString()
        valoracionInterna = parcel.readString()
        primerApellido = parcel.readString()
        primerNombre = parcel.readString()
        segundoApellido = parcel.readString()
        segundoNombre = parcel.readString()
        tipoDocumento = parcel.readString()
        documentoIdentidad = parcel.readString()
        codigoConsultora = parcel.readString()
        deudaBelcorp = parcel.readString()
        deudaEntidadCrediticia = parcel.readString()
        region = parcel.readString()
        zona = parcel.readString()
        seccion = parcel.readString()
        tipoMoneda = parcel.readString()
        moneda = parcel.readString()
        estadoCliente = parcel.readString()
        campanaIngreso = parcel.readString()
        ultimaCampanaFacturada = parcel.readString()
        codestadoCliente = parcel.readString()
        autorizaPedido = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        valoracionBelcorp = parcel.readString()
        enumEstadoCrediticio = parcel.readValue(Int::class.java.classLoader) as? Int
        mensaje = parcel.readString()
        valid = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        TipoError = parcel.readString()
        bloqueos = parcel.createTypedArrayList(BloqueoModel)
        data = parcel.readString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(pais)
        parcel.writeString(nombreCompleto)
        parcel.writeString(motivoBloqueo)
        parcel.writeValue(tieneBloqueo)
        parcel.writeString(EstadoPostulante)
        parcel.writeString(SubEstadoPostulante)
        parcel.writeString(cumpleValidacionesInternas)
        parcel.writeString(valoracionInterna)
        parcel.writeString(primerApellido)
        parcel.writeString(primerNombre)
        parcel.writeString(segundoApellido)
        parcel.writeString(segundoNombre)
        parcel.writeString(tipoDocumento)
        parcel.writeString(documentoIdentidad)
        parcel.writeString(codigoConsultora)
        parcel.writeString(deudaBelcorp)
        parcel.writeString(deudaEntidadCrediticia)
        parcel.writeString(region)
        parcel.writeString(zona)
        parcel.writeString(seccion)
        parcel.writeString(tipoMoneda)
        parcel.writeString(moneda)
        parcel.writeString(estadoCliente)
        parcel.writeString(campanaIngreso)
        parcel.writeString(ultimaCampanaFacturada)
        parcel.writeString(codestadoCliente)
        parcel.writeValue(autorizaPedido)
        parcel.writeString(valoracionBelcorp)
        parcel.writeValue(enumEstadoCrediticio)
        parcel.writeString(mensaje)
        parcel.writeValue(valid)
        parcel.writeString(TipoError)
        parcel.writeTypedList(bloqueos)
        parcel.writeString(data)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ConsultaCrediticiaInternaModel> {
        override fun createFromParcel(parcel: Parcel): ConsultaCrediticiaInternaModel {
            return ConsultaCrediticiaInternaModel(parcel)
        }

        override fun newArray(size: Int): Array<ConsultaCrediticiaInternaModel?> {
            return arrayOfNulls(size)
        }
    }
}

