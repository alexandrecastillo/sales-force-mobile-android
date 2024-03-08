package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.sync

import biz.belcorp.salesforce.core.domain.entities.campania.Campania
import biz.belcorp.salesforce.core.domain.usecases.sync.SyncType
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import io.reactivex.Completable
import io.reactivex.Single

interface SyncRepository {

    fun sync(ua: String, campaign: Campania, rol: Rol): Single<SyncType>

    fun obtenerFechaSync(): Long

    fun resetFechaSync() : Completable

    fun obtenerFechaEventosSync(): Long

    fun marcarSyncEventosEnProceso(): Completable

    fun marcarSyncEventosTerminado(): Completable

    fun leerFlagDeProceso(): Boolean
}
