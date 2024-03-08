package biz.belcorp.salesforce.base.features.sync.di

import biz.belcorp.salesforce.base.features.sync.HomeSyncManager
import biz.belcorp.salesforce.core.sync.SyncManager
import org.koin.core.qualifier.named
import org.koin.dsl.module


internal val syncModule = module {

    factory<SyncManager>(named<HomeSyncManager>()) {
        HomeSyncManager()
    }
}
