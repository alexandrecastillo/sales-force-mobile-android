package biz.belcorp.salesforce.core.domain.repository.consultant

import biz.belcorp.salesforce.core.domain.entities.people.SyncParams
import biz.belcorp.salesforce.core.domain.usecases.sync.SyncType

interface ConsultantsSyncRepository {
    suspend fun sync(params: SyncParams): SyncType
}
