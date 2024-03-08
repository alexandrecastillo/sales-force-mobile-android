package biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile

sealed class GainDetailViewState {
    class Success(val title: String) : GainDetailViewState()
    class Failed(val message: String) : GainDetailViewState()
}
