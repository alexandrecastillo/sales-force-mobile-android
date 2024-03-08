package biz.belcorp.salesforce.modules.postulants.core.domain.repository.sync

import biz.belcorp.salesforce.core.domain.usecases.sync.SyncType


internal interface SyncPostulantesRepository {

    suspend fun sync(ua: String): SyncType

}
