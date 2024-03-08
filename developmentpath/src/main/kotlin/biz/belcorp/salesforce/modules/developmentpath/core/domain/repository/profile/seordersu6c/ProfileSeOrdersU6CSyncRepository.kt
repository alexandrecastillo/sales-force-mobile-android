package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.profile.seordersu6c

import biz.belcorp.salesforce.core.data.dto.kpis.KpiRequestParams

interface ProfileSeOrdersU6CSyncRepository {
    suspend fun sync(params: KpiRequestParams)
}
