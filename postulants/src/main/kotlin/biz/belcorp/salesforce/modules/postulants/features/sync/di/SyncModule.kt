package biz.belcorp.salesforce.modules.postulants.features.sync.di

import biz.belcorp.salesforce.core.sync.SyncManager
import biz.belcorp.salesforce.modules.postulants.features.sync.ApplicationsSyncManager
import biz.belcorp.salesforce.modules.postulants.features.sync.ConfigParamsSyncManager
import org.koin.core.qualifier.named
import org.koin.dsl.module


internal val syncPostulanteModule = module {

    factory<SyncManager>(named<ConfigParamsSyncManager>()) {
        ConfigParamsSyncManager()
    }
    factory<SyncManager>(named<ApplicationsSyncManager>()) {
        ApplicationsSyncManager()
    }

}
