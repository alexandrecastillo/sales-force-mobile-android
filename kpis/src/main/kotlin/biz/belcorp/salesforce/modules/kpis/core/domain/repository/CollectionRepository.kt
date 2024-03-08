package biz.belcorp.salesforce.modules.kpis.core.domain.repository

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiQueryParams
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.collection.CollectionIndicator

interface CollectionRepository {
    suspend fun sync(request: KpiQueryParams)
    suspend fun getCollectionByCampaigns(
        uaKey: LlaveUA,
        campaigns: List<String>
    ): List<CollectionIndicator>

    suspend fun getCollectionsByParent(
        uaKey: LlaveUA,
        campaign: String,
        days: String
    ): List<CollectionIndicator>

    fun profitSyncDate(): Long
}
