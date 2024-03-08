package biz.belcorp.salesforce.modules.kpis.core.data.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.kpis.core.data.network.di.networkModule
import biz.belcorp.salesforce.modules.kpis.core.data.repository.bonification.di.bonificationModule
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.di.collectionModule
import biz.belcorp.salesforce.modules.kpis.core.data.repository.newcycle.di.newcycleModule
import biz.belcorp.salesforce.modules.kpis.core.data.repository.capitalization.di.capitalizationModules
import biz.belcorp.salesforce.modules.kpis.core.data.repository.common.mappers.KpiQueryMapper
import biz.belcorp.salesforce.modules.kpis.core.data.repository.saleorders.di.saleOrdersModule
import org.koin.core.module.Module
import org.koin.dsl.module

internal val dataModules by lazy {
    listByElementsOf<Module>(
        repositoriesModules,
        networkModule
    )
}

internal val repositoriesModules by lazy {
    listByElementsOf<Module>(
        commonModule,
        bonificationModule,
        capitalizationModules,
        collectionModule,
        saleOrdersModule,
        newcycleModule
    )
}

private val commonModule = module {

    factory { KpiQueryMapper() }

}
