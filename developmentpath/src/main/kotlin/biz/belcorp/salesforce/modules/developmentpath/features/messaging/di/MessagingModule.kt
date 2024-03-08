package biz.belcorp.salesforce.modules.developmentpath.features.messaging.di

import biz.belcorp.salesforce.core.messaging.MessageReceiver
import biz.belcorp.salesforce.modules.developmentpath.features.messaging.ModuleMessageReceiver
import biz.belcorp.salesforce.modules.developmentpath.features.messaging.helpers.RddNotificationHelper
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val messagingModule = module {

    factory { RddNotificationHelper(get(), get(), get()) }

    factory<MessageReceiver>(named<ModuleMessageReceiver>()) {
        ModuleMessageReceiver()
    }

}
