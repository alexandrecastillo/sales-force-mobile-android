package biz.belcorp.salesforce.modules.creditinquiry.core.domain.entity

import biz.belcorp.salesforce.modules.creditinquiry.features.model.DeudaExterna
import biz.belcorp.salesforce.modules.creditinquiry.features.model.MotivoBloqueoDeudaInterna

class ConsultaCrediticia {
    var campanaIngreso: String? = null
    var codigoConsultora: String? = null
    var deudaBelcorp: String? = null
    var deudaEntidadCrediticia: String? = null
    var docIdentidad: String? = null
    var moneda: String? = null
    var region: String? = null
    var seccion: String? = null
    var tipoDocumento: String? = null
    var ultimaCampanaFacturada: String? = null
    var valoracionInterna: String? = null
    var estadoCliente: String? = null
    var primerApellido: String? = null
    var segundoApellido: String? = null
    var primerNombre: String? = null
    var segundoNombre: String? = null
    var tipoMoneda: String? = null
    var valoracionExterna: String? = null
    var motivoBloqueoDeudaInternaList: List<MotivoBloqueoDeudaInterna>? = null
    var deudasExternasList: List<DeudaExterna>? = null
}
