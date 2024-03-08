package biz.belcorp.salesforce.modules.virtualmethodology.core.data.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.virtualmethodology.core.data.network.di.networkModule
import biz.belcorp.salesforce.modules.virtualmethodology.core.data.repository.di.groupSegModule
import org.koin.core.module.Module

val dataModules by lazy {
    listByElementsOf<Module>(
        repositoryModules,
        networkModule
    )
}

val repositoryModules by lazy {
    listByElementsOf<Module>(
        groupSegModule
    )
}
