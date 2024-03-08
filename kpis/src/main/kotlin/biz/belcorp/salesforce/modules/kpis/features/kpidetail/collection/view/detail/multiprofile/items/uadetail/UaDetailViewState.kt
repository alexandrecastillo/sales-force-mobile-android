package biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile.items.uadetail

import biz.belcorp.mobile.components.design.selector.bar.model.SelectorModel
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA

sealed class UaDetailViewState {
    class Success(val items: List<SelectorModel>) : UaDetailViewState()
    class ShowChildDescription(val uaName: String, val personName: String, val isCovered: Boolean) : UaDetailViewState()
    class Failure(val message: String) : UaDetailViewState()
    class UpdateItem(val uaKey: LlaveUA) : UaDetailViewState()
}
