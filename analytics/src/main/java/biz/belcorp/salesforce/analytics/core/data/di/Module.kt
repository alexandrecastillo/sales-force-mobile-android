package biz.belcorp.salesforce.analytics.core.data.di

import biz.belcorp.salesforce.analytics.core.data.repository.di.firebaseRepository
import biz.belcorp.salesforce.core.utils.listByElementsOf
import org.koin.core.module.Module

internal val dataModules by lazy {
    listByElementsOf<Module>(
        repositoriesModules
    )
}

internal val repositoriesModules by lazy {

    listByElementsOf<Module>(
        firebaseRepository
    )
}
