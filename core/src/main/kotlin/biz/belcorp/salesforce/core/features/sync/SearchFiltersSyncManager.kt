package biz.belcorp.salesforce.core.features.sync

import biz.belcorp.salesforce.core.domain.usecases.searchfilters.SearchFiltersUseCase
import biz.belcorp.salesforce.core.sync.SyncGroup
import biz.belcorp.salesforce.core.sync.SyncManager
import org.koin.core.KoinComponent
import org.koin.core.inject

internal class SearchFiltersSyncManager : SyncManager(), KoinComponent {

    private val syncUseCase by inject<SearchFiltersUseCase>()

    override val groups = listOf(
        SyncGroup.LOGIN,
        SyncGroup.SEARCH_FILTERS_EDA
    )

    override suspend fun start() {
        syncUseCase.sync()
    }

}
