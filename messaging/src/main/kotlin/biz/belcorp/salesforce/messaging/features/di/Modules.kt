package biz.belcorp.salesforce.messaging.features.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.messaging.features.messaging.di.messagingModule
import biz.belcorp.salesforce.messaging.features.list.di.messagingListModule
import biz.belcorp.salesforce.messaging.features.news.di.newsModule
import biz.belcorp.salesforce.messaging.features.sync.di.syncModule
import org.koin.core.module.Module


internal val featuresModules by lazy {
    listByElementsOf<Module>(
        messagingModule,
        messagingListModule,
        newsModule,
        syncModule
    )
}
