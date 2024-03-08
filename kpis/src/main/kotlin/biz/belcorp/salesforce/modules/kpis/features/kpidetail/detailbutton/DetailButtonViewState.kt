package biz.belcorp.salesforce.modules.kpis.features.kpidetail.detailbutton

import biz.belcorp.salesforce.modules.kpis.features.kpidetail.detailbutton.model.DetailButtonType

sealed class DetailButtonViewState {
    class Success(val title: String, @DetailButtonType val type: Int) : DetailButtonViewState()
    class Error(val message: String) : DetailButtonViewState()
}
