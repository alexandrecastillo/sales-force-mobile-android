package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.negocio.graficos.capitalizacion.model

import biz.belcorp.mobile.components.charts.bar.model.BarEntrySet

class GraphicCapitalizationSeModel(
    val maxValue: Float,
    val minValue: Float,
    val barEntrySet: BarEntrySet,
    val items: List<CapitalizationSeModel>
)
