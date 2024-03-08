package biz.belcorp.salesforce.modules.orders.core.data.di


import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.orders.core.data.network.di.networkModule
import biz.belcorp.salesforce.modules.orders.core.data.repository.filters.di.filtersModule
import biz.belcorp.salesforce.modules.orders.core.data.repository.orders.di.ordersModule
import org.koin.core.module.Module

internal val dataModules by lazy {
    listByElementsOf<Module>(
        repositoriesModules,
        networkModule
    )
}

internal val repositoriesModules by lazy {
    listByElementsOf<Module>(
        filtersModule,
        ordersModule
    )
}
