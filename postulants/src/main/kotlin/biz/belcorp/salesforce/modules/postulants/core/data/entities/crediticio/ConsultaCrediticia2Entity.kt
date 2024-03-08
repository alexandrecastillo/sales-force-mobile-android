package biz.belcorp.salesforce.modules.postulants.core.data.entities.crediticio


import com.google.gson.annotations.SerializedName

class ConsultaCrediticia2Entity {

    @SerializedName("deudas")
    var deudas: List<DeudaExternaEntity>? = null

    @SerializedName("valoracion")
    var valoraciones: List<ValoracionEntity>? = null

    @SerializedName("consultarDCResult")
    var consultarDCResult: ConsultarDCResultEntity? = null

    @SerializedName("Buro")
    var buro: String? = null

    @SerializedName("TieneDeudas")
    var tieneDeudas: Boolean? = null

    @SerializedName("TieneValoracion")
    var tieneValoracion: Boolean? = null

    @SerializedName("TieneMotivos")
    var tieneMotivos: Boolean? = null

    @SerializedName("Resultado")
    var resultado: String? = null

    @SerializedName("Estado")
    var estado: String? = null

    @SerializedName("Nombre")
    var nombreCompleto: String? = null

    @SerializedName("RespuestaServicio")
    var respuestaServicio: String? = null

    @SerializedName("explicacion")
    var explicaciones: List<ExplicacionDeudaEntity>? = null

    @SerializedName("NomEstado")
    var nomEstado: String? = null

    @SerializedName("preCalificacion")
    var preCalificacion: PreCalificacionEntity? = null

    @SerializedName("HasError")
    var hasError: Boolean? = null

    @SerializedName("motivos")
    var motivos: List<MotivoEntity>? = null

    @SerializedName("Score")
    var score: String? = null

    @SerializedName("PrimerApellido")
    var primerApellido: String? = null

    @SerializedName("SegundoApellido")
    var segundoApellido: String? = null

    @SerializedName("FechaNacimiento")
    var fechaNacimiento: String? = null

    @SerializedName("PrimerNombre")
    var primerNombre: String? = null

    @SerializedName("SegundoNombre")
    var segundoNombre: String? = null

    @SerializedName("TipoDocumento")
    var tipoDocumento: String? = null

    @SerializedName("DocumentoIdentidad")
    var docIdentidad: String? = null

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
    var enumEstadoCrediticio: Int = -1

    @SerializedName("Mensaje")
    var mensaje: String? = null

    @SerializedName("Valid")
    var valid: Boolean? = null

    @SerializedName("TipoError")
    var tipoError: String? = null

    @SerializedName("RequiereAprobacionSAC")
    var requiereAprobacionSAC: Boolean = false

    init {
        deudas = ArrayList()
        valoraciones = ArrayList()
        explicaciones = ArrayList()
        motivos = ArrayList()
    }
}
