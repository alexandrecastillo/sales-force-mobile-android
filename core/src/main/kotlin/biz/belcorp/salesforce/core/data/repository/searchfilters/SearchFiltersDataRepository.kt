package biz.belcorp.salesforce.core.data.repository.searchfilters

import biz.belcorp.salesforce.core.data.network.dto.LegacySyncResponse
import biz.belcorp.salesforce.core.data.network.dto.LegacySyncResponse.Companion.FULL
import biz.belcorp.salesforce.core.data.preferences.SyncSharedPreferences
import biz.belcorp.salesforce.core.data.repository.searchfilters.dto.SearchFiltersDataResponse
import biz.belcorp.salesforce.core.domain.repository.searchfilters.SearchFiltersRepository
import biz.belcorp.salesforce.core.domain.usecases.sync.SyncType
import biz.belcorp.salesforce.core.utils.formatLongTZ
import biz.belcorp.salesforce.core.utils.toDate


internal class SearchFiltersDataRepository(
    private val syncCloudStore: SearchFiltersCloudStore,
    private val syncDataStore: SearchFiltersDataStore,
    private val syncPreferences: SyncSharedPreferences
) : SearchFiltersRepository {

    override suspend fun sync(ua: String): SyncType {
        val result = syncCloudStore.sync(ua)
        return saveByResultType(result).also {
            saveBaseSyncDate(result.fechaServidor)
        }
    }

    private fun saveByResultType(result: LegacySyncResponse.Resultado<SearchFiltersDataResponse>):
        SyncType {
        return when (result.tipo) {
            FULL -> syncDataStore.deleteAndInsert(result.data)
            else -> SyncType.None
        }
    }

    private fun saveBaseSyncDate(fecha: String?) {
        val fechaServidor = fecha?.toDate(formatLongTZ)
        syncPreferences.searchFiltersSyncDate = fechaServidor?.time ?: 0
    }

}
