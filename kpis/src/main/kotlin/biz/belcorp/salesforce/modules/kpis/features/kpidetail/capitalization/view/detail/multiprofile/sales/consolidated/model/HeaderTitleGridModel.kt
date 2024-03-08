package biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.detail.multiprofile.sales.consolidated.model

import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING

open class HeaderTitleGridModel

sealed class GridCapitalizationHeaderColumn : HeaderTitleGridModel() {

    class PEGs(
        val pegsTitle: String = EMPTY_STRING,
        val retentionPegsTitle: String = EMPTY_STRING
    ) : GridCapitalizationHeaderColumn()

    class Capitalization(
        val entriesTitle: String = EMPTY_STRING,
        val reentriesTitle: String = EMPTY_STRING,
        val expensesTitle: String = EMPTY_STRING,
        val capitalizationTitle: String = EMPTY_STRING
    ) : GridCapitalizationHeaderColumn()


}

