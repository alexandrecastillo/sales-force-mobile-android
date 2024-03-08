package biz.belcorp.salesforce.modules.orders.core.domain.di


import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.orders.core.domain.usecases.filters.di.filtersModule
import biz.belcorp.salesforce.modules.orders.core.domain.usecases.orders.di.ordersModule
import org.koin.core.module.Module

internal val domainModules by lazy {
    listByElementsOf<Module>(
        useCasesModules
    )
}

private val useCasesModules by lazy {
    listByElementsOf<Module>(
        filtersModule,
        ordersModule
    )
}
