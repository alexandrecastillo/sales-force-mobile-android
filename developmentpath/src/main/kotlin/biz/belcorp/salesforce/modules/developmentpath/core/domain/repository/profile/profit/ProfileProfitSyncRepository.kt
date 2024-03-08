package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.profile.profit

import biz.belcorp.salesforce.core.data.dto.kpis.KpiRequestParams

interface ProfileProfitSyncRepository {
    suspend fun sync(params: KpiRequestParams)
}
