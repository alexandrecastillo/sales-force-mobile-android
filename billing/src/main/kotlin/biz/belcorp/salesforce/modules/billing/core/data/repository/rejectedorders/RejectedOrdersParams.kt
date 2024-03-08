package biz.belcorp.salesforce.modules.billing.core.data.repository.rejectedorders

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA

class RejectedOrdersParams(
    val countryIso: String,
    val uaKey: LlaveUA,
    val campaignCode: String,
    val forceSync: Boolean = false
)
