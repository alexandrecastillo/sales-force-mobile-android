package biz.belcorp.salesforce.base.features.splash.di

import biz.belcorp.salesforce.base.features.splash.SplashViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val splashModule = module {
    viewModel { SplashViewModel(get(), get()) }
}
