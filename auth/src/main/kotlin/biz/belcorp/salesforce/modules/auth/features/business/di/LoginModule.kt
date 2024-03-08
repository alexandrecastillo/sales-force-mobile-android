package biz.belcorp.salesforce.modules.auth.features.business.di

import biz.belcorp.salesforce.modules.auth.features.business.LoginBusinessViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {

    viewModel { LoginBusinessViewModel(get(), get(), get(),get(), get()) }

}
