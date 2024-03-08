package biz.belcorp.salesforce.modules.creditinquiry.core.domain.entity

import biz.belcorp.salesforce.modules.creditinquiry.features.model.DeudaExterna

class ConsultaCrediticia2 {
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
    var consultarDCResult: ConsultarDCResult? = null
    var tieneMotivos: Boolean? = null
    var resultado: String? = null
    var estado: String? = null
    var respuestaServicio: String? = null
    var nomEstado: String? = null
    var preCalificacion: PreCalificacion? = null
    var hasError: Boolean? = null
    var buro: String? = null
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
    var deudas: List<DeudaExterna>? = null
    var valoraciones: List<Valoracion>? = null
    var explicaciones: List<Explicacion>? = null
    var motivos: List<Motivo>? = null
    var requiereAprobacionSAC: Boolean = false

    var isConsultantApt: Boolean = enumEstadoCrediticio == 2
}
