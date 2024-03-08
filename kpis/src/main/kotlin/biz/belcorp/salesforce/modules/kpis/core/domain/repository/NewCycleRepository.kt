package biz.belcorp.salesforce.modules.kpis.core.domain.repository

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiQueryParams
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.newcycle.NewCycleIndicator

interface NewCycleRepository {
    suspend fun sync(request: KpiQueryParams)

    suspend fun getNewCycleByCampaigns(
        uaKey: LlaveUA,
        campaigns: List<String>
    ): List<NewCycleIndicator>

    suspend fun getNewCycleListByParent(
        uaKey: LlaveUA,
        campaigns: List<String>
    ): List<NewCycleIndicator>
}
