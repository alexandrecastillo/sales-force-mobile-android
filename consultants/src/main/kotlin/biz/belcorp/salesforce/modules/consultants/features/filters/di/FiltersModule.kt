package biz.belcorp.salesforce.modules.consultants.features.filters.di

import android.content.Context
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.mappers.ConsultantModelCreator
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.mappers.ConsultantsMapper
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.mappers.FilterConsultantMapper
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.provider.FilterContentProvider
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.view.ConsultantsSearchable
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.view.SearchConsultantViewModel
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.view.filter.FilterDialogViewModel
import biz.belcorp.salesforce.modules.consultants.features.filters.utils.ConsultantsTextResolver
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val filtersModule = module {
    factory { FilterContentProvider(get<Context>().resources) }
    factory { ConsultantsTextResolver(get()) }
    factory { ConsultantModelCreator(get()) }
    factory { ConsultantsMapper(get()) }
    factory { ConsultantsSearchable() }
    factory { FilterConsultantMapper() }

    viewModel { SearchConsultantViewModel(get(), get(), get(), get(), get(), get(), get(), get()) }
    viewModel { FilterDialogViewModel(get()) }
}
