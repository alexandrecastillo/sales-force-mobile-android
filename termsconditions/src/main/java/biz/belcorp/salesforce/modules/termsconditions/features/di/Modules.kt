package biz.belcorp.salesforce.modules.termsconditions.features.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.termsconditions.features.dialog.di.linkModule
import biz.belcorp.salesforce.modules.termsconditions.features.messaging.di.messagingModule
import biz.belcorp.salesforce.modules.termsconditions.features.termsconditions.di.termsConditionsModule
import biz.belcorp.salesforce.modules.termsconditions.features.sync.di.syncModule
import org.koin.core.module.Module

internal val featureModules by lazy {
    listByElementsOf<Module>(
        termsConditionsModule,
        syncModule,
        linkModule,
        messagingModule
    )
}
