package biz.belcorp.salesforce.modules.inspires.features.sync.di

import biz.belcorp.salesforce.core.sync.SyncManager
import biz.belcorp.salesforce.modules.inspires.features.sync.InspiresSyncManager
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val syncModule = module {

    factory<SyncManager>(named<InspiresSyncManager>()) {
        InspiresSyncManager()
    }

}
