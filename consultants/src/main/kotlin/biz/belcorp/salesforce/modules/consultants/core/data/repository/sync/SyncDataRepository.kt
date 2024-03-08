package biz.belcorp.salesforce.modules.consultants.core.data.repository.sync

import biz.belcorp.salesforce.core.data.preferences.SyncSharedPreferences
import biz.belcorp.salesforce.core.domain.usecases.sync.SyncType
import biz.belcorp.salesforce.core.utils.formatLongTZ
import biz.belcorp.salesforce.core.utils.toDate
import biz.belcorp.salesforce.modules.consultants.core.data.repository.sync.dto.SyncResponse
import biz.belcorp.salesforce.modules.consultants.core.data.repository.sync.dto.SyncResponse.Resultado.Companion.FULL
import biz.belcorp.salesforce.modules.consultants.core.data.repository.sync.dto.SyncResponse.Resultado.Companion.PARCIAL
import biz.belcorp.salesforce.modules.consultants.core.domain.repository.sync.SyncRepository


class SyncDataRepository(
    private val syncCloudStore: SyncCloudStore,
    private val syncDataStore: SyncDataStore,
    private val syncPreferences: SyncSharedPreferences
) : SyncRepository {

    override suspend fun sync(ua: String): SyncType {
        val result = syncCloudStore.sync(syncPreferences.legacyConsultantsSyncDate, ua)
        return saveByResultType(result).also {
            saveBaseSyncDate(result.fechaServidor)
        }
    }

    private fun saveByResultType(result: SyncResponse.Resultado): SyncType {
        return when (result.tipo) {
            FULL -> syncDataStore.deleteAndInsert(result.data)
            PARCIAL -> syncDataStore.updateOrInsert(result.data)
            else -> SyncType.None
        }
    }

    private fun saveBaseSyncDate(fecha: String?) {
        val fechaServidor = fecha?.toDate(formatLongTZ)
        syncPreferences.legacyConsultantsSyncDate = fechaServidor?.time ?: 0
    }

}
