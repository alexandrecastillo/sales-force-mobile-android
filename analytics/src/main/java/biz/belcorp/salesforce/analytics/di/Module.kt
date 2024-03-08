package biz.belcorp.salesforce.analytics.di

import biz.belcorp.salesforce.analytics.core.data.di.dataModules
import biz.belcorp.salesforce.analytics.core.domain.di.domainModules
import biz.belcorp.salesforce.analytics.features.di.featuresModules
import biz.belcorp.salesforce.core.utils.listByElementsOf
import com.google.firebase.analytics.FirebaseAnalytics
import org.koin.core.module.Module
import org.koin.dsl.module


val analyticsFeatureModules by lazy {
    listByElementsOf<Module>(
        domainModules,
        dataModules,
        featuresModules,
        firebaseModule
    )
}

internal val firebaseModule = module {
    single { FirebaseAnalytics.getInstance(get()) }
}
