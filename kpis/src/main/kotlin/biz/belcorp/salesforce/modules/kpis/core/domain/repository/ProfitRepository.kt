package biz.belcorp.salesforce.modules.kpis.core.domain.repository

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiQueryParams
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.collection.ProfitIndicator

interface ProfitRepository {
    suspend fun sync(request: KpiQueryParams)
    suspend fun getProfitByCampaigns(uaKey: LlaveUA, campaigns: List<String>): List<ProfitIndicator>
}
