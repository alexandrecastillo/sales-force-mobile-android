package biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.detail.multiprofile.sales.consolidated.grid

import biz.belcorp.mobile.components.design.spreadsheet.models.Column


open class GridCapitalizationViewState {

    class Success(val data: List<Column>) : GridCapitalizationViewState()

    object NonDataAvailable : GridCapitalizationViewState()

}
