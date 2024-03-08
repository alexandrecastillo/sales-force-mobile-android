package biz.belcorp.salesforce.modules.billing.features.billing.view

import biz.belcorp.mobile.components.design.selector.bar.model.SelectorModel
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA

sealed class BillingViewState {
    class Success(val uas: List<SelectorModel>) : BillingViewState()
    class ShowChildDescription(
        val uaName: String,
        val personName: String,
        val isCovered: Boolean
    ) : BillingViewState()

    object HideChildDescription : BillingViewState()
    object Failure : BillingViewState()
    class UpdateItem(val uaKey: LlaveUA) : BillingViewState()
}
