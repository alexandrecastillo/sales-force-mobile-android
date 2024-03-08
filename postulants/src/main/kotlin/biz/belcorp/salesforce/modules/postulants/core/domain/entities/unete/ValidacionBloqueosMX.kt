package biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete

import biz.belcorp.salesforce.modules.postulants.utils.Constant

class ValidacionBloqueosMX {

    var postulante: Postulante? = null
    var estado: String = Constant.EMPTY_STRING
    var ciudad: String = Constant.EMPTY_STRING
    var direccion: String = Constant.EMPTY_STRING
    var tarjetaDeCredito: Boolean = false
    var creditoHipotecario: Boolean = false
    var creditoAutomotriz: Boolean = false
}
