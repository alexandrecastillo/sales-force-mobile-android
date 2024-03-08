package biz.belcorp.salesforce.modules.digital.core.data.di

import biz.belcorp.salesforce.modules.digital.core.data.network.di.networkModule
import biz.belcorp.salesforce.modules.digital.core.data.repository.di.repositoryModule

val dataModules by lazy {
    listOf(
        networkModule,
        repositoryModule
    )
}
