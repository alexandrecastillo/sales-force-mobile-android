package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.buscador.di

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.buscador.BuscarPersonasCercaUseCase
import org.koin.dsl.module

internal val buscadorPersonasModule = module {
    factory { BuscarPersonasCercaUseCase(get(), get(), get(), get()) }
}
