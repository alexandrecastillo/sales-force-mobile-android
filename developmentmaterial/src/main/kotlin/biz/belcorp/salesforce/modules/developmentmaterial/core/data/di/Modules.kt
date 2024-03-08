package biz.belcorp.salesforce.modules.developmentmaterial.core.data.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.developmentmaterial.core.data.network.di.networkModule
import biz.belcorp.salesforce.modules.developmentmaterial.core.data.repository.di.documentsModule
import org.koin.core.module.Module

val dataModules by lazy {
    listByElementsOf<Module>(
        repositoryModules,
        networkModule
    )
}

val repositoryModules by lazy {
    listByElementsOf<Module>(
        documentsModule
    )
}
