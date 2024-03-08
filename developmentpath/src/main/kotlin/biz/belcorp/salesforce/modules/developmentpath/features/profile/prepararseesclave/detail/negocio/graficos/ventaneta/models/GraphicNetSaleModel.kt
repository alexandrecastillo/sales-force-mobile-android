package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.ventaneta.models

import biz.belcorp.mobile.components.charts.bar.model.BarEntrySet

data class GraphicNetSaleModel(
    val netSales: List<NetSaleSeModel>,
    val barEntrySet: BarEntrySet,
    val minValue: Float,
    val maxValue: Float
)