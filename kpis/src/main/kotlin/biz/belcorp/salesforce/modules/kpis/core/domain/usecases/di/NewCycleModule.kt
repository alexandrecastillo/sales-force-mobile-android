package biz.belcorp.salesforce.modules.kpis.core.domain.usecases.di

import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.newcycle.GetNewCycleIndicatorUseCase
import org.koin.dsl.module

val newCycleModule = module {
    factory { GetNewCycleIndicatorUseCase(get(), get(), get(), get(), get()) }
}
