package biz.belcorp.salesforce.modules.kpis.core.domain.usecases.di

import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.KpiDetailParamsUseCase
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.dashboard.KpiDashboardUseCase
import org.koin.dsl.module

val kpiModule = module {
    factory { KpiDashboardUseCase(get(), get(), get(), get(), get(), get(), get()) }
    factory { KpiDetailParamsUseCase(get(), get()) }
}
