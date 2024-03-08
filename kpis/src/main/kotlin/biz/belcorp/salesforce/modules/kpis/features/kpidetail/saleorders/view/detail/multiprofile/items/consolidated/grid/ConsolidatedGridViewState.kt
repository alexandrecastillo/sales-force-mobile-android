package biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.detail.multiprofile.items.consolidated.grid

import biz.belcorp.mobile.components.design.spreadsheet.models.Column

sealed class ConsolidatedGridViewState {
    class Success(val columns: List<Column>) : ConsolidatedGridViewState()
    object NonDataAvailable : ConsolidatedGridViewState()
}
