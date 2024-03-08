package biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.header.multiprofile

sealed class GainHeaderViewState {
    class Success(val data: GainHeaderModel) : GainHeaderViewState()
    class Failed(val message: String) : GainHeaderViewState()
}
