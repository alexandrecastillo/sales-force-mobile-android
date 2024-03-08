package biz.belcorp.salesforce.analytics.features.di

import biz.belcorp.salesforce.analytics.features.BelcorpAnalytics
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val featuresModules = module {
    factory<BelcorpAnalytics>(named<BelcorpAnalytics>()) {
        BelcorpAnalytics
    }

}
