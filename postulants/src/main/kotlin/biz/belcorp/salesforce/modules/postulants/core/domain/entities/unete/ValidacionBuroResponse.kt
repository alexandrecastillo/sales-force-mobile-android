package biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete

import biz.belcorp.salesforce.modules.postulants.utils.Constant

class ValidacionBuroResponse {

    var enumEstadoCrediticio: Int? = null
    var requiereAprobacionSAC: Boolean? = false
    var mensaje: String? = Constant.EMPTY_STRING
    var respuestaServicio: String? = Constant.EMPTY_STRING
}
