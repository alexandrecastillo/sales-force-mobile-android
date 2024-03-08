package biz.belcorp.salesforce.core.domain.usecases.searchfilters.di

import biz.belcorp.salesforce.core.domain.usecases.searchfilters.SearchFiltersUseCase
import org.koin.dsl.module

internal val searchFiltersModule = module {
    factory { SearchFiltersUseCase(get(), get()) }
}
