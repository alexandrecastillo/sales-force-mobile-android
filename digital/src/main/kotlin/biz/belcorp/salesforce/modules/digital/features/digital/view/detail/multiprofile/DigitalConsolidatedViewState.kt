package biz.belcorp.salesforce.modules.digital.features.digital.view.detail.multiprofile

import biz.belcorp.mobile.components.design.spreadsheet.models.Column

sealed class DigitalConsolidatedViewState {
    class Success(val columns: List<Column>) : DigitalConsolidatedViewState()
}
