package biz.belcorp.salesforce.messaging.core.data.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.messaging.core.data.network.di.networkModule
import biz.belcorp.salesforce.messaging.core.data.repository.di.notificationsModule
import org.koin.core.module.Module


internal val dataModules by lazy {
    listByElementsOf<Module>(
        notificationsModule,
        networkModule
    )
}
