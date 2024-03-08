package biz.belcorp.salesforce.base.core.data.di

import biz.belcorp.salesforce.base.core.data.network.di.networkModule
import biz.belcorp.salesforce.base.core.data.repository.options.di.optionsModule
import biz.belcorp.salesforce.core.utils.listByElementsOf
import org.koin.core.module.Module


internal val dataModules by lazy {
    listByElementsOf<Module>(
        repositoriesModules,
        networkModule
    )
}

internal val repositoriesModules by lazy {
    listByElementsOf<Module>(
        optionsModule
    )
}
