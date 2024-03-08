package biz.belcorp.salesforce.modules.billing.features.billing.view.detail

import biz.belcorp.salesforce.modules.billing.features.billing.model.detail.BillingDetailMultiProfileModel
import biz.belcorp.salesforce.modules.billing.features.billing.model.detail.BillingRejectedOrderModel

sealed class BillingMultiProfileDetailViewState {
    class Success(val model: BillingDetailMultiProfileModel) : BillingMultiProfileDetailViewState()
    class Failure(val message: String) : BillingMultiProfileDetailViewState()
}


sealed class RejectedOrdersViewState {
    class Success(val model: List<BillingRejectedOrderModel>) : RejectedOrdersViewState()
    object EmptyView : RejectedOrdersViewState()
    object Failure : RejectedOrdersViewState()
}
