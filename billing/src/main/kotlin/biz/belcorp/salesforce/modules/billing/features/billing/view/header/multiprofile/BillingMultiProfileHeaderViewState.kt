package biz.belcorp.salesforce.modules.billing.features.billing.view.header.multiprofile

import biz.belcorp.salesforce.modules.billing.features.billing.model.BillingHeaderMultiProfileModel


sealed class BillingMultiProfileHeaderViewState {
    class Success(val model: BillingHeaderMultiProfileModel) : BillingMultiProfileHeaderViewState()
    class Failure(val message: String) : BillingMultiProfileHeaderViewState()
}
