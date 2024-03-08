package biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete

import biz.belcorp.salesforce.modules.postulants.utils.Constant

class IndicadorUnete {
    var campaniaActual: String? = Constant.EMPTY_STRING
    var seccion: String? = Constant.EMPTY_STRING
    var enEvaluacion: Int? = Constant.NUMERO_CERO
    var preAprobadas: Int? = Constant.NUMERO_CERO
    var aprobadas: Int? = Constant.NUMERO_CERO
    var rechazadas: Int? = Constant.NUMERO_CERO
    var conversion: Int? = Constant.NUMERO_CERO
    var diasEnEspera: Int? = Constant.NUMERO_CERO
    var ingresosAnticipados: Int? = Constant.NUMERO_CERO
    var preInscritas: Int? = Constant.NUMERO_CERO
    var regularizarDocumento: Int? = Constant.NUMERO_CERO
}
