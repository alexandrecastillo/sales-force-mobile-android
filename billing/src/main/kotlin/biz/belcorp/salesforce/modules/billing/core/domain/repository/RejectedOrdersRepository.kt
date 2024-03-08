package biz.belcorp.salesforce.modules.billing.core.domain.repository

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.billing.core.data.repository.rejectedorders.RejectedOrdersParams
import biz.belcorp.salesforce.modules.billing.core.domain.entities.RejectedOrdersBilling

interface RejectedOrdersRepository {

    suspend fun sync(params: RejectedOrdersParams)
    suspend fun getRejectedOrders(uaKey: LlaveUA, campaignCode: String): List<RejectedOrdersBilling>
}
