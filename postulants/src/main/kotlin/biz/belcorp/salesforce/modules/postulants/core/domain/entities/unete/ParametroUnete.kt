package biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete

import biz.belcorp.salesforce.modules.postulants.utils.Constant

class ParametroUnete {
    var idParametroUnete: Int = Constant.NUMERO_CERO
    var idParametroUnetePadre: Int = Constant.NUMERO_CERO
    var tipoParametro: Int = Constant.NUMERO_CERO
    var nombre: String? = null
    var descripcion: String? = null
    var valor: String? = null
    var estado: String? = null
}
