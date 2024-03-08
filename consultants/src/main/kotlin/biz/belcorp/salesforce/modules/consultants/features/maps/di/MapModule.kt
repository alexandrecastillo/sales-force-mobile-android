package biz.belcorp.salesforce.modules.consultants.features.maps.di

import biz.belcorp.salesforce.modules.consultants.features.maps.MapViewModel
import biz.belcorp.salesforce.modules.consultants.features.maps.mappers.MapMapper
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mapModule = module {
    factory { MapMapper() }
    viewModel { MapViewModel(get(), get(), get(), get(), get()) }
}
