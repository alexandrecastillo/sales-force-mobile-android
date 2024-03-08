package biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete

import biz.belcorp.salesforce.modules.postulants.utils.Constant

class ValidacionBuro {
    var pais: String = Constant.EMPTY_STRING
    var documento: String = Constant.EMPTY_STRING
    var zona: String = Constant.EMPTY_STRING
    var seccion: String = Constant.EMPTY_STRING
    var tipoDocumento: Int = Constant.NUMERO_CERO
    var subestado: Int = Constant.NUMERO_CERO
    var postulante: Int = Constant.NUMERO_CERO
}
