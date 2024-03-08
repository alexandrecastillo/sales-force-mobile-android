package biz.belcorp.salesforce.modules.brightpath.features.sync.di

import biz.belcorp.salesforce.core.sync.SyncManager
import biz.belcorp.salesforce.modules.brightpath.features.sync.BrightPathSyncManager
import org.koin.core.qualifier.named
import org.koin.dsl.module

val syncModule = module {

    factory<SyncManager>(named<BrightPathSyncManager>()) {
        BrightPathSyncManager()
    }

}
