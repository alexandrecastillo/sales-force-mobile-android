package biz.belcorp.salesforce.modules.kpis.core.data.repository.collection

import biz.belcorp.salesforce.core.data.preferences.SyncSharedPreferences
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.cloud.CollectionCloudStore
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.cloud.dto.CollectionQuery
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.data.CollectionDataStore
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.mapper.CollectionMapper
import biz.belcorp.salesforce.modules.kpis.core.data.repository.common.mappers.KpiQueryMapper
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiQueryParams
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.collection.CollectionIndicator
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.CollectionRepository

class CollectionDataRepository(
    private val collectionCloudStore: CollectionCloudStore,
    private val collectionDataStore: CollectionDataStore,
    private val syncPreferences: SyncSharedPreferences,
    private val collectionMapper: CollectionMapper,
    private val queryMapper: KpiQueryMapper
) : CollectionRepository {

    override suspend fun sync(request: KpiQueryParams) {
        val query = CollectionQuery(queryMapper.mapCollection(request))
        val data = collectionCloudStore.getCollection(query)
        val collections = collectionMapper.map(data)
        collectionDataStore.saveCollections(collections)
        syncPreferences.profitSyncDate = System.currentTimeMillis()
    }

    override suspend fun getCollectionByCampaigns(
        uaKey: LlaveUA,
        campaigns: List<String>
    ): List<CollectionIndicator> {
        val data = collectionDataStore.getCollectionByCampaigns(uaKey, campaigns)
        return data.map { collectionMapper.map(it) }
    }

    override suspend fun getCollectionsByParent(
        uaKey: LlaveUA,
        campaign: String,
        days: String
    ): List<CollectionIndicator> {
        val data = collectionDataStore.getCollectionsByParent(uaKey, campaign,days)
        return data.map { collectionMapper.map(it) }
    }

    override fun profitSyncDate(): Long {
        return syncPreferences.profitSyncDate
    }
}
