package biz.belcorp.salesforce.core.domain.repository.campania

import biz.belcorp.salesforce.core.constants.Constant.LIMITE_MAX_CAMPANIAS
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.usecases.sync.SyncType
import io.reactivex.Single

interface CampaniasRepository {

    suspend fun obtenerCampaniaActualSuspend(llaveUA: LlaveUA): Campania

    suspend fun getPreviousCampaignSuspend(llaveUA: LlaveUA): Campania

    fun obtenerCampaniaActualSingle(llaveUA: LlaveUA): Single<Campania>

    fun obtenerCampaniaActual(llaveUA: LlaveUA): Campania?

    fun obtenerCampanias(llaveUA: LlaveUA): Single<List<Campania>>

    fun obtenerPenultimasCampaniasSingle(
        llaveUA: LlaveUA,
        limite: Int = LIMITE_MAX_CAMPANIAS
    ): Single<List<Campania>>

    fun obtenerPenultimasCampanias(
        llaveUA: LlaveUA,
        limite: Int = LIMITE_MAX_CAMPANIAS
    ): List<Campania>

    fun obtenerCampaniaInmediataAnteriorSingle(llaveUA: LlaveUA): Single<Campania?>

    fun obtenerCampaniaInmediataAnterior(llaveUA: LlaveUA): Campania

    fun obtenerCampaniasSincrono(llaveUA: LlaveUA): List<Campania>

    suspend fun sync(ua: String): SyncType

    fun minutesFromLastSync(): Int

    fun minutesCycleSync(): Int

}
