package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.profile.capitalization

import biz.belcorp.salesforce.core.data.dto.kpis.KpiRequestParams

interface ProfileSeCapitalizationSyncRepository {
    suspend fun sync(params: KpiRequestParams)
}
