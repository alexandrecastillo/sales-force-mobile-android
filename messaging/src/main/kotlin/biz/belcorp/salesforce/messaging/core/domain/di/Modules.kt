package biz.belcorp.salesforce.messaging.core.domain.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.messaging.core.domain.usecases.di.notificationsModule
import org.koin.core.module.Module


internal val domainModules by lazy {
    listByElementsOf<Module>(
        notificationsModule
    )
}
