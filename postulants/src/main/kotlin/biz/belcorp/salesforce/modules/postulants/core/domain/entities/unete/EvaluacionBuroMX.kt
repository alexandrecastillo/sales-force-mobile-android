package biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete

import biz.belcorp.salesforce.modules.postulants.utils.Constant

class EvaluacionBuroMX {
    var postulante: Postulante? = null
    var fechaNacimiento: String = Constant.EMPTY_STRING
    var estado: String = Constant.EMPTY_STRING
    var ciudad: String = Constant.EMPTY_STRING
    var direccion: String = Constant.EMPTY_STRING
    var tarjetaDeCredito: String = Constant.EMPTY_STRING
    var creditoHipotecario: String = Constant.EMPTY_STRING
    var creditoAutomotriz: String = Constant.EMPTY_STRING
}
