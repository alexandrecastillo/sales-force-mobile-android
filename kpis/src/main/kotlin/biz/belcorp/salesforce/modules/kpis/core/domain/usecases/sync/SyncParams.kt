package biz.belcorp.salesforce.modules.kpis.core.domain.usecases.sync

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA

class SyncParams(
    val ua: LlaveUA,
    val countryIso: String,
    val isBilling: Boolean,
    val isForced: Boolean = false,
    val hasToBeUpdated: Boolean = false
)
