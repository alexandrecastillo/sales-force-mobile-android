package biz.belcorp.salesforce.modules.billing.core.data.repository.mapper

import biz.belcorp.salesforce.core.entities.capitalization.CapitalizationEntity
import biz.belcorp.salesforce.core.entities.newcycles.NewCycleEntity
import biz.belcorp.salesforce.modules.billing.core.domain.entities.NewCycleBilling
import biz.belcorp.salesforce.modules.billing.core.domain.entities.PegsBilling

class BillingDetailMapper {

    fun map(model: CapitalizationEntity): PegsBilling = with(model) {
        return PegsBilling(
            retainedPegs = pegRetentionReal,
            totalPegs = pegRetentionGoal
        )
    }

    fun map(model: NewCycleEntity): NewCycleBilling = with(model) {
        return NewCycleBilling(
            highValueOrders6d6 = highValueOrders6d6,
            highValueOrders5d5 = highValueOrders5d5,
            highValueOrders4d4 = highValueOrders4d4,
            highValueOrders3d3 = highValueOrders3d3,
            lowValueOrders6d6 = lowValueOrders6d6,
            lowValueOrders5d5 = lowValueOrders5d5,
            lowValueOrders4d4 = lowValueOrders4d4,
            lowValueOrders3d3 = lowValueOrders3d3,
            lowValueOrders2d2 = lowValueOrders2d2,
            lowValueOrders1d1 = lowValueOrders1d1
        )
    }
}
