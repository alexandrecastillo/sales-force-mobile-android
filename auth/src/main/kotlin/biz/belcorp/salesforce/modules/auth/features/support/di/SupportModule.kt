package biz.belcorp.salesforce.modules.auth.features.support.di

import biz.belcorp.salesforce.modules.auth.features.support.LoginSupportViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val supportModule = module {
    viewModel { LoginSupportViewModel(get(), get(), get(), get(), get()) }
}
