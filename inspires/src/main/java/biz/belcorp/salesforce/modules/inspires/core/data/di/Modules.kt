package biz.belcorp.salesforce.modules.inspires.core.data.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.inspires.core.data.network.di.networkModule
import biz.belcorp.salesforce.modules.inspires.core.data.repository.di.inspiresModule
import biz.belcorp.salesforce.modules.inspires.core.data.repository.sync.di.syncModule
import org.koin.core.module.Module

val dataModules by lazy {
    listByElementsOf<Module>(
        repositoriesModules,
        networkModule
    )
}

internal val repositoriesModules by lazy {
    listByElementsOf<Module>(
        syncModule,
        inspiresModule
    )
}
