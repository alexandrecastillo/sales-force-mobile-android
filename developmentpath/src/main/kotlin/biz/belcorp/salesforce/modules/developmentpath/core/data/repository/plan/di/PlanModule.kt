package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.plan.di

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.plan.cloud.PlanDetalleCloudDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.plan.data.PlanDetalleLocalDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.plan.data.PlanRddDbDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.plan.data.PlanRutaDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.plan.PlanDetalleDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.online.PlanDetalleRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.plan.RddPlanRepository
import org.koin.dsl.module

internal val planModule = module {

    factory { PlanRutaDBDataStore() }

    factory { PlanDetalleLocalDataStore(eventosxUaDatabase = get()) }
    factory { PlanDetalleCloudDataStore(syncRestApi = get(), apiCallHelper = get()) }
    factory<PlanDetalleRepository> {
        PlanDetalleDataRepository(database = get(), cloud = get())
    }

    factory<RddPlanRepository> { PlanRddDbDataStore() }
}
