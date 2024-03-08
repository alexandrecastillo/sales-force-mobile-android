package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.dashboard.di

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.dashboard.*
import org.koin.dsl.module

internal val dashboardModule = module {
    factory { AvanceRegionesUseCase(get(), get(), get()) }
    factory { AvanceSeccionesUseCase(get(), get(), get(), get()) }
    factory { AvanceZonasUseCase(get(), get(), get(), get()) }
    factory { ObtenerAvanceVisitasMiEquipoUseCase(get(), get(), get(), get(), get()) }
    factory { ObtenerMisVisitasPropiasUseCase(get(), get(), get()) }
}
