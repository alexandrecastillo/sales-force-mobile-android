package biz.belcorp.salesforce.modules.orders.core.data.repository.filters.di

import biz.belcorp.salesforce.modules.orders.core.data.repository.filters.FiltersDataMapper
import biz.belcorp.salesforce.modules.orders.core.data.repository.filters.FiltersDataRepository
import biz.belcorp.salesforce.modules.orders.core.data.repository.filters.FiltersDataStore
import biz.belcorp.salesforce.modules.orders.core.domain.repository.FiltersRepository
import org.koin.dsl.module


val filtersModule = module {

    factory { FiltersDataMapper() }

    factory { FiltersDataStore() }

    factory<FiltersRepository> { FiltersDataRepository(get(), get()) }

}
