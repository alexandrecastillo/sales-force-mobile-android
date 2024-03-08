package biz.belcorp.salesforce.modules.kpis.core.domain.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.di.*
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.di.kpiSyncModule
import org.koin.core.module.Module


internal val domainModules by lazy {
    listByElementsOf<Module>(
        useCasesModules
    )
}

private val useCasesModules by lazy {
    listByElementsOf<Module>(
        capitalizationModule,
        newCycleModule,
        collectionModule,
        saleOrdersModule,
        kpiModule,
        kpiSyncModule,
        detailButtonModule
    )
}
