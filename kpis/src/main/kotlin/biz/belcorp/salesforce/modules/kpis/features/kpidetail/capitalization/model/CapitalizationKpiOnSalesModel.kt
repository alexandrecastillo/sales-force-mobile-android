package biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.model

import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.model.CoupledModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.DefaultGridModel

data class CapitalizationKpiOnSalesModel(
    val resultsLabel: String,
    val potentialKpiModel: PossiblesPotentialKpiModel,
    val retentionPercentage: String,
    val capitalizationKpi: List<CoupledModel.GridWithHeaderItemModel>,
    val tooltip: String
)

data class PossiblesPotentialKpiModel(
    val potentialKpi: List<DefaultGridModel>,
    val totalPotentialKpiLabel: String
)
