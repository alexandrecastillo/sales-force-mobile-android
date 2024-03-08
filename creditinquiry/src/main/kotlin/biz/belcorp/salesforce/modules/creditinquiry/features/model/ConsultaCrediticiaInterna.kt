package biz.belcorp.salesforce.modules.creditinquiry.features.model

import biz.belcorp.salesforce.modules.creditinquiry.core.domain.entity.Bloqueo

class ConsultaCrediticiaInterna {
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
    var bloqueos: List<Bloqueo>? = null
    var data: String? = null
}
