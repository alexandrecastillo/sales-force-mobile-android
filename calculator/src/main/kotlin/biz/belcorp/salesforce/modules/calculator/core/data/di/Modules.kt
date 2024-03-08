package biz.belcorp.salesforce.modules.calculator.core.data.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.calculator.core.data.network.di.networkModule
import biz.belcorp.salesforce.modules.calculator.core.data.repository.sync_new.di.syncModule
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
    )
}
