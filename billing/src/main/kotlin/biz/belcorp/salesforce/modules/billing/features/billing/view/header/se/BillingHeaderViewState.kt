package biz.belcorp.salesforce.modules.billing.features.billing.view.header.se

import biz.belcorp.salesforce.modules.billing.features.billing.model.BillingHeaderModel

sealed class BillingHeaderViewState(val model: BillingHeaderModel) {

    class Success(model: BillingHeaderModel) : BillingHeaderViewState(model)

}
