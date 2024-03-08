package biz.belcorp.salesforce.core.domain.usecases.terms.di

import biz.belcorp.salesforce.core.domain.usecases.terms.SyncTermsConditionsUseCase
import biz.belcorp.salesforce.core.domain.usecases.terms.TermConditionsUseCase
import org.koin.dsl.module

val termsConditionsModule = module {
    factory { TermConditionsUseCase(get(),get(),get()) }
    factory { SyncTermsConditionsUseCase(get(), get()) }
}
