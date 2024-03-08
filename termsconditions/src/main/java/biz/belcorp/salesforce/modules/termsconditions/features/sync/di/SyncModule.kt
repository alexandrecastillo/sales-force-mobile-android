package biz.belcorp.salesforce.modules.termsconditions.features.sync.di

import biz.belcorp.salesforce.core.sync.SyncManager
import biz.belcorp.salesforce.modules.termsconditions.features.sync.TermsConditionsSyncManager
import org.koin.core.qualifier.named
import org.koin.dsl.module

internal val syncModule = module {

    factory<SyncManager>(named<TermsConditionsSyncManager>()) {
        TermsConditionsSyncManager()
    }

}
