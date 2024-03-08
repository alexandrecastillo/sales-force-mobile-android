package biz.belcorp.salesforce.core.features.sync.di

import biz.belcorp.salesforce.core.features.sync.CampaignsSyncManager
import biz.belcorp.salesforce.core.features.sync.SearchFiltersSyncManager
import biz.belcorp.salesforce.core.sync.SyncManager
import org.koin.core.qualifier.named
import org.koin.dsl.module


internal val syncModule = module {

    factory<SyncManager>(named<SearchFiltersSyncManager>()) { SearchFiltersSyncManager() }

    factory<SyncManager>(named<CampaignsSyncManager>()) { CampaignsSyncManager() }

}
