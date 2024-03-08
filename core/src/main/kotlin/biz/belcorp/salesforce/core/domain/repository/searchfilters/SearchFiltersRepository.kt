package biz.belcorp.salesforce.core.domain.repository.searchfilters

import biz.belcorp.salesforce.core.domain.usecases.sync.SyncType


interface SearchFiltersRepository {

    suspend fun sync(ua: String): SyncType

}
