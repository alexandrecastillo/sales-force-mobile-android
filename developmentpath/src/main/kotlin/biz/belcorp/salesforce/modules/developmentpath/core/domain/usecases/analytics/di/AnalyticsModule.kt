package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.analytics.di

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.analytics.FirebaseAnalyticsUseCase
import org.koin.dsl.module

internal val analyticsModule = module {
    factory { FirebaseAnalyticsUseCase(get(), get(), get(), get(), get(), get(), get(), get(), get()) }
}
