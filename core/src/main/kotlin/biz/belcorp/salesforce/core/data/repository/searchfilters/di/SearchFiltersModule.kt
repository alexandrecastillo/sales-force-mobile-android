package biz.belcorp.salesforce.core.data.repository.searchfilters.di

import biz.belcorp.salesforce.core.data.repository.searchfilters.SearchFiltersCloudStore
import biz.belcorp.salesforce.core.data.repository.searchfilters.SearchFiltersDataRepository
import biz.belcorp.salesforce.core.data.repository.searchfilters.SearchFiltersDataStore
import biz.belcorp.salesforce.core.domain.repository.searchfilters.SearchFiltersRepository
import org.koin.dsl.module


internal val searchFiltersModule = module {

    factory { SearchFiltersCloudStore(get(), get()) }
    factory { SearchFiltersDataStore() }

    factory<SearchFiltersRepository> { SearchFiltersDataRepository(get(), get(), get()) }

}
