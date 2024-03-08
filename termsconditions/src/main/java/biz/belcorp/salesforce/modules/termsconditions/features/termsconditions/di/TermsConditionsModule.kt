package biz.belcorp.salesforce.modules.termsconditions.features.termsconditions.di

import biz.belcorp.salesforce.modules.termsconditions.features.termsconditions.TermsConditionsMapper
import biz.belcorp.salesforce.modules.termsconditions.features.termsconditions.TermsConditionsViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val termsConditionsModule = module {
    viewModel { TermsConditionsViewModel(get(), get()) }
    factory { TermsConditionsMapper() }
}
