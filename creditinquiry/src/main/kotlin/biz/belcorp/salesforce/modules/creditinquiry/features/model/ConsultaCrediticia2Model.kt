package biz.belcorp.salesforce.modules.creditinquiry.features.model

import android.os.Parcel
import android.os.Parcelable

class ConsultaCrediticia2Model() : Parcelable {

    var codigoConsultora: String? = null
    var docIdentidad: String? = null
    var estadoCliente: String? = null
    var primerApellido: String? = null
    var segundoApellido: String? = null
    var primerNombre: String? = null
    var segundoNombre: String? = null
    var nombreCompleto: String? = null
    var campanaIngreso: String? = null
    var ultimaCampanaFacturada: String? = null
    var tieneDeudas: Boolean? = null
    var tieneValoracion: Boolean? = null
    var consultarDCResult: ConsultarDCResultModel? = null
    var tieneMotivos: Boolean? = null
    var resultado: String? = null
    var estado: String? = null
    var respuestaServicio: String? = null
    var nomEstado: String? = null
    var preCalificacion: PreCalificacionModel? = null
    var hasError: Boolean? = null
    var score: String? = null
    var tipoDocumento: String? = null
    var deudaBelcorp: String? = null
    var deudaEntidadCrediticia: String? = null
    var region: String? = null
    var zona: String? = null
    var seccion: String? = null
    var tipoMoneda: String? = null
    var moneda: String? = null
    var codestadoCliente: String? = null
    var autorizaPedido: Boolean? = null
    var valoracionBelcorp: String? = null
    var enumEstadoCrediticio: Int = -1
    var mensaje: String? = null
    var valid: Boolean? = null
    var tipoError: String? = null
    var buro: String? = null
    var deudas: List<DeudaExternaModel>? = null
    var valoraciones: List<ValoracionModel>? = null
    var explicaciones: List<ExplicacionModel>? = null
    var motivos: List<MotivoModel>? = null
    var isConsultantApt: Boolean = false

    constructor(parcel: Parcel) : this() {
        codigoConsultora = parcel.readString()
        docIdentidad = parcel.readString()
        estadoCliente = parcel.readString()
        primerApellido = parcel.readString()
        segundoApellido = parcel.readString()
        primerNombre = parcel.readString()
        segundoNombre = parcel.readString()
        nombreCompleto = parcel.readString()
        campanaIngreso = parcel.readString()
        ultimaCampanaFacturada = parcel.readString()
        tieneDeudas = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        tieneValoracion = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        consultarDCResult = parcel.readParcelable(ConsultarDCResultModel::class.java.classLoader)
        tieneMotivos = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        resultado = parcel.readString()
        estado = parcel.readString()
        respuestaServicio = parcel.readString()
        nomEstado = parcel.readString()
        preCalificacion = parcel.readParcelable(PreCalificacionModel::class.java.classLoader)
        hasError = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        score = parcel.readString()
        tipoDocumento = parcel.readString()
        deudaBelcorp = parcel.readString()
        deudaEntidadCrediticia = parcel.readString()
        region = parcel.readString()
        zona = parcel.readString()
        seccion = parcel.readString()
        tipoMoneda = parcel.readString()
        moneda = parcel.readString()
        codestadoCliente = parcel.readString()
        autorizaPedido = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        valoracionBelcorp = parcel.readString()
        enumEstadoCrediticio = parcel.readInt()
        mensaje = parcel.readString()
        valid = parcel.readValue(Boolean::class.java.classLoader) as? Boolean
        tipoError = parcel.readString()
//        data = parcel.readString()
        buro = parcel.readString()
        deudas = parcel.createTypedArrayList(DeudaExternaModel.CREATOR)
        valoraciones = parcel.createTypedArrayList(ValoracionModel.CREATOR)
        explicaciones = parcel.createTypedArrayList(ExplicacionModel.CREATOR)
        motivos = parcel.createTypedArrayList(MotivoModel)
        isConsultantApt = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(codigoConsultora)
        parcel.writeString(docIdentidad)
        parcel.writeString(estadoCliente)
        parcel.writeString(primerApellido)
        parcel.writeString(segundoApellido)
        parcel.writeString(primerNombre)
        parcel.writeString(segundoNombre)
        parcel.writeString(nombreCompleto)
        parcel.writeString(campanaIngreso)
        parcel.writeString(ultimaCampanaFacturada)
        parcel.writeValue(tieneDeudas)
        parcel.writeValue(tieneValoracion)
        parcel.writeParcelable(consultarDCResult, flags)
        parcel.writeValue(tieneMotivos)
        parcel.writeString(resultado)
        parcel.writeString(estado)
        parcel.writeString(respuestaServicio)
        parcel.writeString(nomEstado)
        parcel.writeParcelable(preCalificacion, flags)
        parcel.writeValue(hasError)
        parcel.writeString(score)
        parcel.writeString(tipoDocumento)
        parcel.writeString(deudaBelcorp)
        parcel.writeString(deudaEntidadCrediticia)
        parcel.writeString(region)
        parcel.writeString(zona)
        parcel.writeString(seccion)
        parcel.writeString(tipoMoneda)
        parcel.writeString(moneda)
        parcel.writeString(codestadoCliente)
        parcel.writeValue(autorizaPedido)
        parcel.writeString(valoracionBelcorp)
        parcel.writeInt(enumEstadoCrediticio)
        parcel.writeString(mensaje)
        parcel.writeValue(valid)
        parcel.writeString(tipoError)
//        parcel.writeString(data)
        parcel.writeString(buro)
        parcel.writeTypedList(deudas)
        parcel.writeTypedList(valoraciones)
        parcel.writeTypedList(explicaciones)
        parcel.writeTypedList(motivos)
        parcel.writeByte(if (isConsultantApt) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ConsultaCrediticia2Model> {
        override fun createFromParcel(parcel: Parcel): ConsultaCrediticia2Model {
            return ConsultaCrediticia2Model(parcel)
        }

        override fun newArray(size: Int): Array<ConsultaCrediticia2Model?> {
            return arrayOfNulls(size)
        }
    }

}

