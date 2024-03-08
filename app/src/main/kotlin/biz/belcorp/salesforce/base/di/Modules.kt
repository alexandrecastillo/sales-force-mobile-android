package biz.belcorp.salesforce.base.di

import android.app.Application
import biz.belcorp.salesforce.analytics.di.analyticsFeatureModules
import biz.belcorp.salesforce.base.core.data.di.dataModules
import biz.belcorp.salesforce.base.core.domain.di.domainModules
import biz.belcorp.salesforce.base.features.di.featureModules
import biz.belcorp.salesforce.core.di.coreModules
import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.messaging.di.messagingModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.module.Module

internal fun injectModules(app: Application) {
    startKoin {
        androidLogger()
        androidContext(app)
        modules(baseModules)
    }
}

val baseModules by lazy {
    listByElementsOf<Module>(
        featureModules,
        dataModules,
        domainModules,
        coreModules,
        messagingModules,
        analyticsFeatureModules
    )
}


