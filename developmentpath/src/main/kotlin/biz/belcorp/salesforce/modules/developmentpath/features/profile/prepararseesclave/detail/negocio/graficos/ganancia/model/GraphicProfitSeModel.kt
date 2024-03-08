package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.ganancia.model

import biz.belcorp.mobile.components.charts.bar.model.BarEntrySet

class GraphicProfitSeModel(
    val maxValue: Float,
    val minValue: Float,
    val barEntrySet: BarEntrySet,
    val average: String,
    val items: List<ProfitSeModel>
)
