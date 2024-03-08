package biz.belcorp.salesforce.modules.orders.core.domain.usecases.filters.di

import biz.belcorp.salesforce.modules.orders.core.domain.usecases.filters.GetSearchFiltersUseCase
import org.koin.dsl.module


val filtersModule = module {

    factory { GetSearchFiltersUseCase(get(), get(), get()) }

}
