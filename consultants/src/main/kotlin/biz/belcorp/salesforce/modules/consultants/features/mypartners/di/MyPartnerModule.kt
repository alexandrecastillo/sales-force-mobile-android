package biz.belcorp.salesforce.modules.consultants.features.mypartners.di

import biz.belcorp.salesforce.modules.consultants.features.mypartners.MyPartnersViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val myPartnerViewModel = module {
    viewModel { MyPartnersViewModel(get(), get(), get()) }
}
