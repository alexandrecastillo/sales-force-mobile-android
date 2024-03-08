package biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete

import biz.belcorp.salesforce.modules.postulants.utils.Constant

class FiltroBusqueda {

    var pais: String? = Constant.EMPTY_STRING
    var region: String? = Constant.EMPTY_STRING
    var zona: String? = Constant.EMPTY_STRING
    var seccion: String? = Constant.EMPTY_STRING
    var rol: String? = Constant.EMPTY_STRING
    var tipoFiltro: Int = Constant.NUMERO_CERO
    var textoBusqueda: String = Constant.EMPTY_STRING
}
