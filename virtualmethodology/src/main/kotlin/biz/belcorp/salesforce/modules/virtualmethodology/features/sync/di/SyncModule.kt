package biz.belcorp.salesforce.modules.virtualmethodology.features.sync.di

import biz.belcorp.salesforce.core.sync.SyncManager
import biz.belcorp.salesforce.modules.virtualmethodology.features.sync.VirtualMethodologySyncManager
import org.koin.core.qualifier.named
import org.koin.dsl.module


internal val syncModule = module {

    factory<SyncManager>(named<VirtualMethodologySyncManager>()) {
        VirtualMethodologySyncManager()
    }

}
