package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.retencionactivas.models

import biz.belcorp.mobile.components.charts.bar.model.BarEntrySet

class GraphicActivesRetentionModel(
    val maxValue: Float,
    val minValue: Float,
    val year: String,
    val barEntrySet: BarEntrySet,
    val indicatorValue: Float
)
