package biz.belcorp.salesforce.modules.kpis.core.domain.repository

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiQueryParams
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.collection.RetentionIndicator

interface RetentionRepository {
    suspend fun sync(request: KpiQueryParams)
    suspend fun getRetentionByCampaigns(uaKey: LlaveUA, campaigns: List<String>) : List<RetentionIndicator>
}
