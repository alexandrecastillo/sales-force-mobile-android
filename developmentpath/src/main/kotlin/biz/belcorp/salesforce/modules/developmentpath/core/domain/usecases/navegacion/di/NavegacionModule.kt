package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.navegacion.di

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.navegacion.BarraNavegacionUseCase
import org.koin.dsl.module

internal val navegacionModule = module {
    factory { BarraNavegacionUseCase(get(), get(), get(), get(), get()) }
}
