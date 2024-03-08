package biz.belcorp.salesforce.modules.billing.core.data.repository

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.billing.core.data.repository.mapper.BillingDetailDataStore
import biz.belcorp.salesforce.modules.billing.core.data.repository.mapper.BillingDetailMapper
import biz.belcorp.salesforce.modules.billing.core.domain.entities.NewCycleBilling
import biz.belcorp.salesforce.modules.billing.core.domain.entities.PegsBilling
import biz.belcorp.salesforce.modules.billing.core.domain.repository.BillingDetailRepository

class BillingDetailDataRepository(
    private val dataStore: BillingDetailDataStore,
    private val mapper: BillingDetailMapper
) : BillingDetailRepository {

    override suspend fun getPegsRetainedOrders(
        uaKey: LlaveUA,
        campaignsCode: List<String>
    ): List<PegsBilling> {
        val data = dataStore.getPegsRetainedOrders(uaKey, campaignsCode)
            .sortedBy { it.campaign }
        return data.map { mapper.map(it) }
    }

    override suspend fun geNewCyclePendingOrders(
        uaKey: LlaveUA,
        campaignsCode: List<String>
    ): List<NewCycleBilling> {
        val data = dataStore.getNewCyclePendingOrders(uaKey, campaignsCode)
            .sortedBy { it.campaign }
        return data.map { mapper.map(it) }
    }
}
