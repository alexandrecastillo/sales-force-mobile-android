package biz.belcorp.salesforce.modules.developmentpath.features.analytics.di

import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import org.koin.dsl.module

val analyticsModule = module {
    factory { FirebaseAnalyticsPresenter(get()) }
}
