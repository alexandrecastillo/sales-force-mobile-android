package biz.belcorp.salesforce.analytics.core.domain.usecases.di

import biz.belcorp.salesforce.analytics.core.domain.usecases.firebase.FirebaseAnalyticsUseCase
import org.koin.dsl.module

internal val analyticsModule = module {
    factory { FirebaseAnalyticsUseCase(get(), get(),get()) }

}
