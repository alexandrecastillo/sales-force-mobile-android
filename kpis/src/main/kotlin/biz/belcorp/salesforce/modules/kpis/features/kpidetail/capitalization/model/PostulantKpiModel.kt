package biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.model

import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.DefaultGridModel

data class PostulantKpiModel(
    val capitalizationLabel: String,
    val kpiValues: List<DefaultGridModel>
)
