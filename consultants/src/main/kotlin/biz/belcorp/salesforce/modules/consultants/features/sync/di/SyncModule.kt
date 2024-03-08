package biz.belcorp.salesforce.modules.consultants.features.sync.di

import biz.belcorp.salesforce.core.sync.SyncManager
import biz.belcorp.salesforce.modules.consultants.features.sync.ConsultantsPendingDebtSyncManager
import biz.belcorp.salesforce.modules.consultants.features.sync.ConsultantsSyncManager
import biz.belcorp.salesforce.modules.consultants.features.sync.LegacyConsultantsSyncManager
import org.koin.core.qualifier.named
import org.koin.dsl.module


internal val syncModule = module {

    factory<SyncManager>(named<LegacyConsultantsSyncManager>()) {
        LegacyConsultantsSyncManager()
    }
    factory<SyncManager>(named<ConsultantsSyncManager>()) {
        ConsultantsSyncManager()
    }
    factory<SyncManager>(named<ConsultantsPendingDebtSyncManager>()) {
        ConsultantsPendingDebtSyncManager()
    }

}
