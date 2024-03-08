package biz.belcorp.salesforce.modules.calculator.core.data.repository.sync.di

import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync.SyncCloudStore
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync.SyncDataStore
import org.koin.dsl.module

val syncModuleOld = module {

    factory { SyncCloudStore(get(), get()) }
    factory { SyncDataStore() }

//    factory<SyncRepository> { SyncDataRepository(get(), get(), get()) }

}
