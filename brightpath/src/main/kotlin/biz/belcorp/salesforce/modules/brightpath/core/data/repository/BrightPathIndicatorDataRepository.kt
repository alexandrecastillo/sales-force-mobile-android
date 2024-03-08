package biz.belcorp.salesforce.modules.brightpath.core.data.repository

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.usecases.sync.SyncType
import biz.belcorp.salesforce.modules.brightpath.core.data.mapper.mapToLevelIndicator
import biz.belcorp.salesforce.modules.brightpath.core.data.repository.cloud.BrightPathIndicatorCloudStore
import biz.belcorp.salesforce.modules.brightpath.core.data.repository.data.birghtpath.BrightPathIndicatorDataStore
import biz.belcorp.salesforce.modules.brightpath.core.data.repository.data.birghtpath.BrightPathIndicatorSyncDataStore
import biz.belcorp.salesforce.modules.brightpath.core.domain.entities.LevelIndicator
import biz.belcorp.salesforce.modules.brightpath.core.domain.repository.BrightPathIndicatorRepository


class BrightPathIndicatorDataRepository(
    private val dataStore: BrightPathIndicatorDataStore,
    private val syncDataStore: BrightPathIndicatorSyncDataStore,
    private val cloudStore: BrightPathIndicatorCloudStore
) : BrightPathIndicatorRepository {

    override suspend fun getIndicatorDataAsync(uaKey: LlaveUA): LevelIndicator {
        return dataStore
            .getIndicatorData(uaKey)
            .mapToLevelIndicator()
    }

    override suspend fun sync(uaKey: String): SyncType {
        val result = cloudStore.sync(uaKey)
        syncDataStore.deleteAndInsert(result.data)
        return SyncType.None
    }
}
