package biz.belcorp.salesforce.modules.consultants.core.domain.repository.sync

import biz.belcorp.salesforce.core.domain.usecases.sync.SyncType


interface SyncRepository {

    suspend fun sync(ua: String): SyncType

}
