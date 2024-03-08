package biz.belcorp.salesforce.modules.consultants.core.data.repository.sync.di

import biz.belcorp.salesforce.modules.consultants.core.data.repository.sync.SyncCloudStore
import biz.belcorp.salesforce.modules.consultants.core.data.repository.sync.SyncDataRepository
import biz.belcorp.salesforce.modules.consultants.core.data.repository.sync.SyncDataStore
import biz.belcorp.salesforce.modules.consultants.core.domain.repository.sync.SyncRepository
import org.koin.dsl.module

val syncModule = module {

    factory { SyncCloudStore(get(), get()) }
    factory { SyncDataStore() }

    factory<SyncRepository> { SyncDataRepository(get(), get(), get()) }

}
