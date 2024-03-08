package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.desempenio.di

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.desempenio.ObtenerDesempenioGrGzUseCase
import org.koin.dsl.module

internal val desempenioModule = module {
    factory { ObtenerDesempenioGrGzUseCase(get(), get(), get(), get(), get()) }
}
