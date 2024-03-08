package biz.belcorp.salesforce.messaging.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.messaging.core.data.di.dataModules
import biz.belcorp.salesforce.messaging.core.domain.di.domainModules
import biz.belcorp.salesforce.messaging.features.di.featuresModules
import org.koin.core.module.Module

val messagingModules by lazy {
    listByElementsOf<Module>(
        dataModules,
        domainModules,
        featuresModules
    )
}
