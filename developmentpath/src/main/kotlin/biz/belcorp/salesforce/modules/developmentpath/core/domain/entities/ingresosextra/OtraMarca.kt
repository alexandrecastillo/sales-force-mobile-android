package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.ingresosextra

import biz.belcorp.salesforce.core.constants.Constant

data class OtraMarca(
    var marcaDetalleId: Long = Constant.UNO_NEGATIVO.toLong(),
    var marcaId: Long = Constant.UNO_NEGATIVO.toLong(),
    var consultoraId: Long = Constant.UNO_NEGATIVO.toLong(),
    var codigoRegion: String = Constant.HYPHEN,
    var codigoZona: String = Constant.HYPHEN,
    var codigoSeccion: String = Constant.HYPHEN,
    var name: String? = Constant.EMPTY_STRING,
    var iconoId: Int = Constant.UNO_NEGATIVO,
    var orden: Int = Constant.NUMERO_CERO,
    var showFront: Boolean = true,
    var checked: Boolean = false,
    var categoria: String = Constant.EMPTY_STRING,
    var campania: String = Constant.EMPTY_STRING
)
