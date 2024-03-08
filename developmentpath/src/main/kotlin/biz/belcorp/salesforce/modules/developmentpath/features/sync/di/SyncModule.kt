package biz.belcorp.salesforce.modules.developmentpath.features.sync.di

import biz.belcorp.salesforce.core.sync.SyncManager
import biz.belcorp.salesforce.modules.developmentpath.features.sync.DashboardSingleSyncManager
import biz.belcorp.salesforce.modules.developmentpath.features.sync.DevelopmentPathSyncManager
import org.koin.core.qualifier.named
import org.koin.dsl.module


internal val syncModule = module {

    factory<SyncManager>(named<DevelopmentPathSyncManager>()) {
        DevelopmentPathSyncManager()
    }

    factory<SyncManager>(named<DashboardSingleSyncManager>()) {
        DashboardSingleSyncManager()
    }

}
