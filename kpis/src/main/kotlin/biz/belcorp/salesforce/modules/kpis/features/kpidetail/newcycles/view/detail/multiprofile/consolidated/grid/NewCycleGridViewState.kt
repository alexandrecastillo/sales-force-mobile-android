package biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.view.detail.multiprofile.consolidated.grid

import biz.belcorp.mobile.components.design.spreadsheet.models.Column

sealed class NewCycleGridViewState {
    class Success(val columns: List<Column>) : NewCycleGridViewState()
    object NonDataAvailable : NewCycleGridViewState()
}
