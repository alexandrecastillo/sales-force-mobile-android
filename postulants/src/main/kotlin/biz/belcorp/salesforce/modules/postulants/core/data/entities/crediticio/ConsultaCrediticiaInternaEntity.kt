package biz.belcorp.salesforce.modules.postulants.core.data.entities.crediticio

import com.google.gson.annotations.SerializedName

class ConsultaCrediticiaInternaEntity {

    @SerializedName("pais")
    var pais: String? = null

    @SerializedName("NombreCompleto")
    var nombreCompleto: String? = null

    @SerializedName("MotivoBloqueo")
    var motivoBloqueo: String? = null

    @SerializedName("TieneBloqueo")
    var tieneBloqueo: Boolean? = null

    @SerializedName("EstadoPostulante")
    var estadoPostulante: String? = null

    @SerializedName("SubEstadoPostulante")
    var subEstadoPostulante: String? = null

    @SerializedName("CumpleValidacionesInternas")
    var cumpleValidacionesInternas: String? = null

    @SerializedName("ValoracionInterna")
    var valoracionInterna: String? = null

    @SerializedName("PrimerApellido")
    var primerApellido: String? = null

    @SerializedName("PrimerNombre")
    var primerNombre: String? = null

    @SerializedName("SegundoApellido")
    var segundoApellido: String? = null

    @SerializedName("SegundoNombre")
    var segundoNombre: String? = null

    @SerializedName("TipoDocumento")
    var tipoDocumento: String? = null

    @SerializedName("DocumentoIdentidad")
    var documentoIdentidad: String? = null

    @SerializedName("CodigoConsultora")
    var codigoConsultora: String? = null

    @SerializedName("DeudaBelcorp")
    var deudaBelcorp: String? = null

    @SerializedName("DeudaEntidadCrediticia")
    var deudaEntidadCrediticia: String? = null

    @SerializedName("Region")
    var region: String? = null

    @SerializedName("Zona")
    var zona: String? = null

    @SerializedName("Seccion")
    var seccion: String? = null

    @SerializedName("TipoMoneda")
    var tipoMoneda: String? = null

    @SerializedName("Moneda")
    var moneda: String? = null

    @SerializedName("EstadoCliente")
    var estadoCliente: String? = null

    @SerializedName("CampanaIngreso")
    var campanaIngreso: String? = null

    @SerializedName("UltimaCampanaFacturada")
    var ultimaCampanaFacturada: String? = null

    @SerializedName("codestadoCliente")
    var codestadoCliente: String? = null

    @SerializedName("AutorizaPedido")
    var autorizaPedido: Boolean? = null

    @SerializedName("ValoracionBelcorp")
    var valoracionBelcorp: String? = null

    @SerializedName("EnumEstadoCrediticio")
    var enumEstadoCrediticio: Int? = null

    @SerializedName("Mensaje")
    var mensaje: String? = null

    @SerializedName("Valid")
    var valid: Boolean? = null

    @SerializedName("TipoError")
    var tipoError: String? = null

    @SerializedName("Bloqueos")
    var bloqueos: List<BloqueoEntity>? = null

    @SerializedName("Data")
    var data: String? = null

    init {
        bloqueos = ArrayList()
    }

}
