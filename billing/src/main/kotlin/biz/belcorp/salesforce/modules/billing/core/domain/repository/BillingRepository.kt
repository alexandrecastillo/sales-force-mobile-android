package biz.belcorp.salesforce.modules.billing.core.domain.repository

import biz.belcorp.salesforce.core.domain.entities.billing.Billing
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA

interface BillingRepository {
    suspend fun getBillingAdvancement(uaKey: LlaveUA): Billing
}
