package biz.belcorp.salesforce.modules.billing.features.billing.mapper.detail

import biz.belcorp.salesforce.modules.billing.core.domain.entities.BillingMultiProfileDetailContainer
import biz.belcorp.salesforce.modules.billing.features.billing.model.detail.BillingDetailMultiProfileModel

class BillingMultiProfileDetailMapper(
    private val billingPegMapper: BillingPegsMapper,
    private val billingNewCycleMapper: BillingNewCycleMapper
) {

    fun map(model: BillingMultiProfileDetailContainer): BillingDetailMultiProfileModel =
        with(model) {
            val retainedPegs = billingPegMapper.map(isThirdPerson, retainedPegs)
            val newCycle = billingNewCycleMapper.map(isThirdPerson, newCyclePendings)
            return BillingDetailMultiProfileModel(retainedPegs, newCycle.second, newCycle.first)
        }
}
