package biz.belcorp.salesforce.modules.postulants.core.data.repository.sync

import biz.belcorp.salesforce.core.data.network.dto.LegacySyncResponse
import biz.belcorp.salesforce.core.data.network.dto.LegacySyncResponse.Companion.FULL
import biz.belcorp.salesforce.core.data.preferences.SyncSharedPreferences
import biz.belcorp.salesforce.core.domain.usecases.sync.SyncType
import biz.belcorp.salesforce.core.utils.formatLongTZ
import biz.belcorp.salesforce.core.utils.toDate
import biz.belcorp.salesforce.modules.postulants.core.data.repository.sync.dto.DataResponse
import biz.belcorp.salesforce.modules.postulants.core.domain.repository.sync.SyncPostulantesRepository


internal class SyncPostulantesDataRepository(
    private val syncCloudStore: SyncPostulantesCloudStore,
    private val syncDataStore: SyncPostulantesDataStore,
    private val syncPreferences: SyncSharedPreferences
) : SyncPostulantesRepository {

    override suspend fun sync(ua: String): SyncType {
        val result = syncCloudStore.sync(ua)
        return saveByResultType(result).also {
            saveBaseSyncDate(result.fechaServidor)
        }
    }

    private fun saveByResultType(result: LegacySyncResponse.Resultado<DataResponse>): SyncType {
        return when (result.tipo) {
            FULL -> syncDataStore.deleteAndInsert(result.data)
            else -> SyncType.None
        }
    }

    private fun saveBaseSyncDate(fecha: String?) {
        val fechaServidor = fecha?.toDate(formatLongTZ)
        syncPreferences.postulantsSyncDate = fechaServidor?.time ?: 0
    }

}
