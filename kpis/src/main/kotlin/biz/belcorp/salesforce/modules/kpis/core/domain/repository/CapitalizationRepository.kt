package biz.belcorp.salesforce.modules.kpis.core.domain.repository

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiQueryParams
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.capitalization.CapitalizationIndicator

interface CapitalizationRepository {
    suspend fun sync(request: KpiQueryParams)
    suspend fun getKpiDataByCampaignsAndUa(
        uaKey: LlaveUA,
        campaigns: List<String>
    ): List<CapitalizationIndicator>

    suspend fun getConsolidatedByUa(
        uaKey: LlaveUA,
        campaign: String
    ): List<CapitalizationIndicator>
}
