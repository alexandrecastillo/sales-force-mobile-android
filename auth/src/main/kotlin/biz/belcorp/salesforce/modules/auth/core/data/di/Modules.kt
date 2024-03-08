package biz.belcorp.salesforce.modules.auth.core.data.di


import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.auth.core.data.network.di.networkModule
import biz.belcorp.salesforce.modules.auth.core.data.repository.auth.di.authModule
import org.koin.core.module.Module

internal val dataModules by lazy {
    listByElementsOf<Module>(
        repositoriesModules,
        networkModule
    )
}

private val repositoriesModules by lazy {
    listByElementsOf<Module>(
        authModule
    )
}
