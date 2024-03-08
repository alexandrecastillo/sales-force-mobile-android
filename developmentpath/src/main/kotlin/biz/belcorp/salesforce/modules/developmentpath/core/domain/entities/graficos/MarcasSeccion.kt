package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos

import biz.belcorp.salesforce.core.constants.Constant

class MarcasSeccion(
    val campania: String? = Constant.EMPTY_STRING,
    var barVisible: Int? = 0,
    val zona: String? = Constant.EMPTY_STRING,
    val seccion: String? = Constant.EMPTY_STRING,
    val esika: Int? = 0,
    val lbel: Int? = 0,
    val cyzone: Int? = 0
)
