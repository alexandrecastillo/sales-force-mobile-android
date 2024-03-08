package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.graficos.pedidosu6c

import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant

data class OrderU6C(
    val campaign: String,
    val orders: Int = Constant.ZERO_NUMBER
)
