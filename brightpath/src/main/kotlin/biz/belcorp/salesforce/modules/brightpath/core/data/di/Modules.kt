package biz.belcorp.salesforce.modules.brightpath.core.data.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.brightpath.core.data.mapper.di.mapperModule
import biz.belcorp.salesforce.modules.brightpath.core.data.network.di.networkModule
import biz.belcorp.salesforce.modules.brightpath.core.data.repository.cloud.di.cloudStoreModule
import biz.belcorp.salesforce.modules.brightpath.core.data.repository.data.di.dataStoreModule
import biz.belcorp.salesforce.modules.brightpath.core.data.repository.di.repositoryModule
import biz.belcorp.salesforce.modules.brightpath.features.sync.di.syncModule
import org.koin.core.module.Module

val dataModule by lazy {
    listByElementsOf<Module>(
        networkModule,
        cloudStoreModule,
        dataStoreModule,
        repositoryModule,
        syncModule,
        mapperModule
    )
}
