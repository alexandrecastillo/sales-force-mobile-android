package biz.belcorp.salesforce.core.data.repository.campania

import biz.belcorp.salesforce.core.data.preferences.SyncSharedPreferences
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import biz.belcorp.salesforce.core.domain.usecases.sync.SyncType
import biz.belcorp.salesforce.core.utils.calculateDiffMinutes
import biz.belcorp.salesforce.core.utils.formatLongTZ
import biz.belcorp.salesforce.core.utils.toDate
import io.reactivex.Single
import java.util.*


class CampaniasDataRepository(
    private val cloudStore: CampaniasCloudStore,
    private val dataStore: CampaniasDataStore,
    private val mapper: CampaniasDataMapper,
    private val syncPreferences: SyncSharedPreferences
) : CampaniasRepository {

    override suspend fun obtenerCampaniaActualSuspend(llaveUA: LlaveUA): Campania {
        val campaniaUaEntity = dataStore.obtenerCampaniaSincrono(llaveUA)
        return mapper.parse(requireNotNull(campaniaUaEntity))
    }

    override suspend fun getPreviousCampaignSuspend(llaveUA: LlaveUA): Campania {
        val campaignUaEntity = dataStore.obtenerCampaniaAnteriorSincrono(llaveUA)
        return mapper.parse(campaignUaEntity)
    }

    override fun obtenerCampaniaActualSingle(llaveUA: LlaveUA): Single<Campania> {
        return dataStore.obtenerCampaniaActual(llaveUA)
            .map { mapper.parse(it) }
    }

    override fun obtenerCampaniaActual(llaveUA: LlaveUA): Campania? {
        return mapper.parse(dataStore.obtenerCampaniaSincrono(llaveUA) ?: return null)
    }

    override fun obtenerCampanias(llaveUA: LlaveUA): Single<List<Campania>> {
        return dataStore.obtenerCampanias(llaveUA)
            .map { mapper.parse(it) }
    }

    override fun obtenerPenultimasCampaniasSingle(
        llaveUA: LlaveUA,
        limite: Int
    ): Single<List<Campania>> {
        return dataStore.obtenerPenultimasCampanias(llaveUA, limite)
            .map { mapper.parse(it) }
    }

    override fun obtenerPenultimasCampanias(llaveUA: LlaveUA, limite: Int): List<Campania> {
        return dataStore.obtenerPenultimasCampaniasSincrono(llaveUA, limite)
            .map { mapper.parse(it) }
    }

    override fun obtenerCampaniaInmediataAnteriorSingle(llaveUA: LlaveUA): Single<Campania?> {
        return dataStore.obtenerCampaniaAnterior(llaveUA)
            .map { mapper.parse(it) }
    }

    override fun obtenerCampaniaInmediataAnterior(llaveUA: LlaveUA): Campania {
        return mapper.parse(dataStore.obtenerCampaniaAnteriorSincrono(llaveUA))
    }

    override fun obtenerCampaniasSincrono(llaveUA: LlaveUA): List<Campania> {
        return mapper.parse(dataStore.obtenerCampaniasSincrono(llaveUA))
    }

    override suspend fun sync(ua: String): SyncType {
        val result = cloudStore.sync(ua)
        return dataStore.saveCampanias(result.data).also {
            saveSyncDate(result.fechaServidor)
        }
    }

    override fun minutesFromLastSync(): Int {
        val syncDate = Date(syncPreferences.campaignsSyncDate)
        return calculateDiffMinutes(otherDate = syncDate).toInt()
    }

    override fun minutesCycleSync() = syncPreferences.cycleSyncCampaignsMinutes

    private fun saveSyncDate(fecha: String?) {
        val fechaServidor = fecha?.toDate(formatLongTZ)
        syncPreferences.campaignsSyncDate = fechaServidor?.time ?: 0
    }
}
