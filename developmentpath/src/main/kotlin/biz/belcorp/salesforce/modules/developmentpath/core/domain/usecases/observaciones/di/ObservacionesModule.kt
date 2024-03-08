package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.observaciones.di

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.observaciones.ObservacionesVisitaUseCase
import org.koin.dsl.module

internal val observacionesModule = module {
    factory { ObservacionesVisitaUseCase(get(), get(), get()) }
}
