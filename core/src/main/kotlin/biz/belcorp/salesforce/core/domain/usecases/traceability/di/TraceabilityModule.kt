package biz.belcorp.salesforce.core.domain.usecases.traceability.di

import biz.belcorp.salesforce.core.domain.usecases.traceability.TraceabilityUseCase
import org.koin.dsl.module

val traceabilityModule = module {
    factory { TraceabilityUseCase(get(), get(),get()) }
}
