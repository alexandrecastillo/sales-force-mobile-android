package biz.belcorp.salesforce.base.features.webpage.di

import biz.belcorp.salesforce.base.features.webpage.WebPageViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module


val webPageModule = module {

    viewModel { WebPageViewModel(get()) }

}
