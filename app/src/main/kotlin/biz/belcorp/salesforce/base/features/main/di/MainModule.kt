package biz.belcorp.salesforce.base.features.main.di

import biz.belcorp.salesforce.base.features.main.MainMapper
import biz.belcorp.salesforce.base.features.main.MainViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule = module {

    factory { MainMapper() }
    viewModel { MainViewModel(get(), get(), get(), get(), get(), get(), get()) }

}
