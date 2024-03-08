package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.sync

import android.os.Build
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.data.preferences.SyncSharedPreferences
import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.usecases.sync.SyncType
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.plan.data.PlanRddDbDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.sync.cloud.SyncCloudStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.sync.SyncResponse
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.sync.SyncRepository
import io.reactivex.Completable
import io.reactivex.Single


internal class SyncDataRepository(
    private val syncDBStore: SyncDataStore,
    private val syncCloudStore: SyncCloudStore,
    private val syncPreferences: SyncSharedPreferences,
    private val planRddDbDataStore: PlanRddDbDataStore
) : SyncRepository {

    companion object {
        private const val TAG_REQUEST = "SyncApiRDD => Request"
        private const val TAG_RESPONSE = "SyncApiRDD => Response"
    }

    lateinit var campaign:Campania
    lateinit var rol: Rol

    override fun sync(ua: String, campaign: Campania, rol: Rol): Single<SyncType> {
        this@SyncDataRepository.campaign = campaign
        this@SyncDataRepository.rol = rol
        val fechaSync = obtenerFechaSync()
        Logger.d(TAG_REQUEST, "$fechaSync - $ua")
        return syncCloudStore.sincronizar(fechaSync, ua)
            .flatMap { resultado ->
                Logger.d(TAG_RESPONSE, "Tipo: " + (resultado.tipo ?: EMPTY_STRING))
                syncDBStore.guardar(resultado)
                    .doAfterSuccess {
                        guardarFechas(resultado)
                    }
            }
    }

    private fun guardarFechas(resultado: SyncResponse.Resultado) {
        guardarFechaSync(resultado.fechaServidor)
        decidirGuardarFechaEventosSync(resultado)
    }

    private fun decidirGuardarFechaEventosSync(resultado: SyncResponse.Resultado) {
        if (resultado.tipo?.equals(SyncResponse.Resultado.FULL) == true) {
            guardarFechaEventosSync(resultado.fechaServidor)
            marcarSyncEventosTerminadoSincrono()
        }
    }

    override fun obtenerFechaSync(): Long {
        val planRDDCampaniaActual = planRddDbDataStore.obtenerCampaniaActualPlanRDD(rol)
        return if(!planRDDCampaniaActual.isNullOrEmpty() && campaign.codigo == planRDDCampaniaActual) {
            syncPreferences.rddSyncDate ?: 0
        }else {
            0
        }
    }

    private fun guardarFechaSync(fecha: String?) {
        val fechaServidor = fecha?.toDate(obtenerFormatoFechaPorVersion())
        syncPreferences.rddSyncDate = fechaServidor?.time
    }

    private fun obtenerFormatoFechaPorVersion(): String {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            formatLongTZ
        } else {
            formatLongT
        }
    }

    override fun resetFechaSync(): Completable {
        return Completable.create {
            syncPreferences.rddSyncDate = null
            it.onComplete()
        }
    }

    private fun guardarFechaEventosSync(fecha: String?) {
        syncPreferences.rddEventsSyncDate = fecha?.toDate(formatLongT)?.time
    }

    override fun obtenerFechaEventosSync(): Long {
        return syncPreferences.rddEventsSyncDate ?: 0
    }

    override fun marcarSyncEventosEnProceso(): Completable {
        return doOnCompletable { marcarSyncEventosEnProcesoSincrono() }
    }

    override fun marcarSyncEventosTerminado(): Completable {
        return doOnCompletable { syncPreferences.flagEventosSyncEnProceso = false }
    }

    override fun leerFlagDeProceso(): Boolean {
        return syncPreferences.flagEventosSyncEnProceso
    }

    private fun marcarSyncEventosTerminadoSincrono() {
        syncPreferences.flagEventosSyncEnProceso = false
    }

    private fun marcarSyncEventosEnProcesoSincrono() {
        syncPreferences.flagEventosSyncEnProceso = true
    }
}
