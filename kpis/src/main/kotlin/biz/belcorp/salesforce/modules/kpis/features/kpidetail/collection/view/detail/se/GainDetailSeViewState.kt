package biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.se

import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.se.model.GainDetailSeModel


sealed class GainDetailSeViewState {
    class Success(val model: GainDetailSeModel) : GainDetailSeViewState()
    class EmptyView(val uaName: String) : GainDetailSeViewState()
    class Failed(val message: String) : GainDetailSeViewState()
}
