package biz.belcorp.salesforce.modules.consultants.core.domain.repository.amount

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA


interface OrdersAmountRepository {

    suspend fun fetch(countryCode: String, uaKey: LlaveUA, campaignCode: String)

}
