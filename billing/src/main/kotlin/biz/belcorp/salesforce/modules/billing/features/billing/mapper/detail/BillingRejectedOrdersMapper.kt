package biz.belcorp.salesforce.modules.billing.features.billing.mapper.detail

import biz.belcorp.salesforce.modules.billing.core.domain.entities.RejectedOrdersBilling
import biz.belcorp.salesforce.modules.billing.features.billing.model.detail.BillingRejectedOrderModel
import java.util.*

class BillingRejectedOrdersMapper {

    private fun map(model: RejectedOrdersBilling): BillingRejectedOrderModel = with(model) {
        return BillingRejectedOrderModel(
            title = title.toLowerCase(Locale.getDefault()).capitalize(),
            quantity = quantity
        )
    }

    fun map(models: List<RejectedOrdersBilling>): List<BillingRejectedOrderModel> {
        return models.map { map(it) }.sortedByDescending { it.quantity }
    }
}
