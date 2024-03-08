package biz.belcorp.salesforce.messaging.features.messaging.di

import biz.belcorp.salesforce.core.messaging.MessageReceiver
import biz.belcorp.salesforce.messaging.features.messaging.ModuleMessageReceiver
import biz.belcorp.salesforce.messaging.features.messaging.NotificationMapper
import biz.belcorp.salesforce.messaging.features.messaging.helpers.NewsNotificationHelper
import biz.belcorp.salesforce.messaging.features.messaging.helpers.PostulantsNotificationHelper
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val messagingModule = module {

    factory { NotificationMapper() }

    factory { NewsNotificationHelper(get(), get(), get()) }
    factory { PostulantsNotificationHelper(get(), get(), get()) }

    factory<MessageReceiver>(named<ModuleMessageReceiver>()) {
        ModuleMessageReceiver()
    }

}
