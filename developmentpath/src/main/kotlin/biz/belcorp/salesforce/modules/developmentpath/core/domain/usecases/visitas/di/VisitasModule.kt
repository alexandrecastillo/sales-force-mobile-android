package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.visitas.di

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.visitas.*
import org.koin.dsl.module

internal val visitasModule = module {
    factory {
        AdicionarVisitaUseCase(
            get(), get(), get(), get(), get(), get(), get(), get(), get(), get()
        )
    }
    factory { CargaInicialRegistroVisitaUseCase(get(), get(), get(), get()) }
    factory { PlanificarVisitaUseCase(get(), get(), get(), get(), get(), get(), get(), get()) }
    factory { RecuperarAvanceVisitasUseCase(get(), get(), get()) }
    factory { RecuperarResultadoVisitasUseCase(get(), get(), get(), get(),get()) }
    factory { RecuperarVisitaUseCase(get(), get(), get()) }
    factory {
        RegistrarVisitaUseCase(
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
    factory { ReprogramarVisitaUseCase(get(), get(), get(), get(), get(), get(), get(), get()) }
}
