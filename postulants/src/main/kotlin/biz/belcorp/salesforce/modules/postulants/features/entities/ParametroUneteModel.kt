package biz.belcorp.salesforce.modules.postulants.features.entities

import biz.belcorp.salesforce.modules.postulants.utils.Constant

class ParametroUneteModel {
    var idParametroUnete: Int = Constant.NUMERO_CERO
    var idParametroUnetePadre: Int = Constant.NUMERO_CERO
    var tipoParametro: Int = Constant.NUMERO_CERO
    var nombre: String? = null
    var descripcion: String? = null
    var valor: String? = null
    var estado: String? = null

    override fun toString() = nombre.orEmpty()
}
