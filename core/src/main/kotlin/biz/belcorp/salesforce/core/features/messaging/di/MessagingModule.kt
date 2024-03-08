package biz.belcorp.salesforce.core.features.messaging.di

import biz.belcorp.salesforce.core.features.messaging.ModuleMessageReceiver
import biz.belcorp.salesforce.core.messaging.MessageReceiver
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val messagingModule = module {

    factory<MessageReceiver>(named<ModuleMessageReceiver>()) {
        ModuleMessageReceiver()
    }

}
