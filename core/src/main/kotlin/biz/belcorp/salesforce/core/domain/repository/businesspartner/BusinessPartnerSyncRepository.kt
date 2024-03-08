package biz.belcorp.salesforce.core.domain.repository.businesspartner

import biz.belcorp.salesforce.core.domain.entities.people.SyncParams

interface BusinessPartnerSyncRepository {

    suspend fun sync(params: SyncParams)
}
