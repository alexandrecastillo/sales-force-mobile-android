package biz.belcorp.salesforce.modules.billing.core.domain.repository

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.billing.core.domain.entities.NewCycleBilling
import biz.belcorp.salesforce.modules.billing.core.domain.entities.PegsBilling

interface BillingDetailRepository {

    suspend fun getPegsRetainedOrders(
        uaKey: LlaveUA,
        campaignsCode: List<String>
    ): List<PegsBilling>

    suspend fun geNewCyclePendingOrders(
        uaKey: LlaveUA,
        campaignsCode: List<String>
    ): List<NewCycleBilling>
}
