package biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.view.header

import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.model.NewCycleIndicatorModel

sealed class NewCycleHeaderViewState {
    class NewCycleIndicatorListSuccess(val kpiNewCycleIndicator: NewCycleIndicatorModel) :
        NewCycleHeaderViewState()

    class NewCycleIndicatorError(val message: String) : NewCycleHeaderViewState()
}
