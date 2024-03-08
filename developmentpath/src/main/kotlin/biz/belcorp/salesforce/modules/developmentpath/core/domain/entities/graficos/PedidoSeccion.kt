package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos

import biz.belcorp.salesforce.core.constants.Constant

class PedidoSeccion(
    var campania: String? = Constant.EMPTY_STRING,
    var region: String? = Constant.EMPTY_STRING,
    var zona: String? = Constant.EMPTY_STRING,
    var seccion: String? = Constant.EMPTY_STRING,
    var pedidosReal: String? = Constant.EMPTY_STRING
)
