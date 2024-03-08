package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.puntaje.di

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.puntaje.PuntajePremioUseCase
import org.koin.dsl.module

internal val puntajeModule = module {
    factory {
        PuntajePremioUseCase(
            get(), get(), get(), get(), get(), get()
        )
    }
}
