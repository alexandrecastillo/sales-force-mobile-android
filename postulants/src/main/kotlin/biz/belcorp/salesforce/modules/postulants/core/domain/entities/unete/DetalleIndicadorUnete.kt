package biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete

import biz.belcorp.salesforce.modules.postulants.utils.Constant

class DetalleIndicadorUnete : DetalleIndicador() {
    var regularizarDocumento: Int? = Constant.NUMERO_CERO
    var enEvaluacion: Int? = Constant.NUMERO_CERO
    var preAprobadas: Int? = Constant.NUMERO_CERO
    var aprobadas: Int? = Constant.NUMERO_CERO
    var ingresosAnticipados: Int? = Constant.NUMERO_CERO

    var campaniaActual: String? = Constant.EMPTY_STRING
    var nombre: String? = Constant.EMPTY_STRING
    var rechazadas: Int? = Constant.NUMERO_CERO
    var conversion: Int? = Constant.NUMERO_CERO
    var diasEnEspera: Int? = Constant.NUMERO_CERO
    var preInscritas: Int? = Constant.NUMERO_CERO
    var proactivoFinalizar: Int? = Constant.NUMERO_CERO
    var proactivoFinalizados: Int? = Constant.NUMERO_CERO
    var proactivoPreAprobados: Int? = Constant.NUMERO_CERO
}
