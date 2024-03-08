package biz.belcorp.salesforce.modules.kpis.core.domain.usecases.di

import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.sync.KpiSyncUseCase
import org.koin.dsl.module

internal val kpiSyncModule = module {
    factory { KpiSyncUseCase(get(), get(), get(), get(), get(), get(), get(), get(), get()) }
}
