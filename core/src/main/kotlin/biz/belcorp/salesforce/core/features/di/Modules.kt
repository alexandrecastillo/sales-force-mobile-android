package biz.belcorp.salesforce.core.features.di

import biz.belcorp.salesforce.core.features.broadcast.SenderEstadoProgress
import biz.belcorp.salesforce.core.features.messaging.di.messagingModule
import biz.belcorp.salesforce.core.features.sync.di.syncModule
import biz.belcorp.salesforce.core.features.uainfo.di.uaInfoModule
import biz.belcorp.salesforce.core.features.utils.CoreTextResolver
import biz.belcorp.salesforce.core.utils.listByElementsOf
import org.koin.core.module.Module
import org.koin.dsl.module

internal val featureModules by lazy {
    listByElementsOf<Module>(
        broadcastModule,
        syncModule,
        messagingModule,
        uaInfoModule,
        utilsModule
    )
}

internal val utilsModule = module {

    factory { CoreTextResolver(get()) }

}

internal val broadcastModule by lazy {
    module {
        factory { SenderEstadoProgress(get()) }
    }
}
