package biz.belcorp.salesforce.modules.billing.core.data.repository

import biz.belcorp.salesforce.core.domain.entities.billing.Billing
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.billing.core.data.repository.mapper.BillingMapper
import biz.belcorp.salesforce.modules.billing.core.domain.repository.BillingRepository

class BillingDataRepository(
    private val dataStore: BillingDataStore,
    private val mapper: BillingMapper
) : BillingRepository {

    override suspend fun getBillingAdvancement(uaKey: LlaveUA): Billing {
        val billingData = dataStore.getBillingAdvancement(uaKey).last()
        return mapper.map(billingData)
    }

}
