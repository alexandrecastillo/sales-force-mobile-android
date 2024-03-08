package biz.belcorp.salesforce.modules.consultants.features.messaging.di

import biz.belcorp.salesforce.core.messaging.MessageReceiver
import biz.belcorp.salesforce.modules.consultants.features.messaging.ModuleMessageReceiver
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val messagingModule = module {

    factory<MessageReceiver>(named<ModuleMessageReceiver>()) {
        ModuleMessageReceiver()
    }

}
