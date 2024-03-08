package biz.belcorp.salesforce.modules.kpis.features.sync.di


import biz.belcorp.salesforce.core.sync.SyncManager
import biz.belcorp.salesforce.modules.kpis.features.sync.CollectionSyncManager
import biz.belcorp.salesforce.modules.kpis.features.sync.KpiSyncManager
import org.koin.core.qualifier.named
import org.koin.dsl.module


internal val syncModule = module {

    factory<SyncManager>(named<CollectionSyncManager>()) {
        CollectionSyncManager()
    }

    factory<SyncManager>(named<KpiSyncManager>()) {
        KpiSyncManager()
    }

}
