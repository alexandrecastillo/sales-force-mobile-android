package biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.model

import biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders.SaleOrdersIndicator

data class SaleOrdersDetailModel(
    val title: String,
    val items: List<ContentModel>,
    val model: SaleOrdersIndicator
)
