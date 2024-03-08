package biz.belcorp.salesforce.modules.brightpath.core.data.repository.cloud.di

import biz.belcorp.salesforce.modules.brightpath.core.data.repository.cloud.BrightPathIndicatorCloudStore
import biz.belcorp.salesforce.modules.brightpath.core.data.repository.cloud.BusinessPartnerChangeLevelCloudStore
import biz.belcorp.salesforce.modules.brightpath.core.data.repository.data.birghtpath.BrightPathIndicatorSyncDataStore
import org.koin.dsl.module

val cloudStoreModule = module {

    factory { BrightPathIndicatorCloudStore(get()) }
    factory { BrightPathIndicatorSyncDataStore() }
    factory { BusinessPartnerChangeLevelCloudStore(get(), get()) }

}
