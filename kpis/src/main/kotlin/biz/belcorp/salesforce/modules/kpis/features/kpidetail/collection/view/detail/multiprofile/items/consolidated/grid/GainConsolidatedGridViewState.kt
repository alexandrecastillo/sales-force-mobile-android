package biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile.items.consolidated.grid

import biz.belcorp.mobile.components.design.spreadsheet.models.Column

sealed class GainConsolidatedGridViewState {
    class Success(val columns: List<Column>) : GainConsolidatedGridViewState()
    object NonDataAvailable : GainConsolidatedGridViewState()
}
