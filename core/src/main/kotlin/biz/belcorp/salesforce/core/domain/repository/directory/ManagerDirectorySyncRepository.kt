package biz.belcorp.salesforce.core.domain.repository.directory

import biz.belcorp.salesforce.core.domain.entities.people.SyncParams


interface ManagerDirectorySyncRepository {
    suspend fun sync(params: SyncParams)
}
