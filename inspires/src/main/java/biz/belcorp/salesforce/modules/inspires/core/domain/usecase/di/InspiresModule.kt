package biz.belcorp.salesforce.modules.inspires.core.domain.usecase.di

import biz.belcorp.salesforce.modules.inspires.core.domain.usecase.*
import org.koin.dsl.module

val inspiresModule = module {
    factory { SyncUseCase(get(), get()) }
    factory { GetIndicatorUseCase(get(), get(), get()) }
    factory { GetInspiraAvancePeriodoUseCase(get(), get(), get()) }
    factory { GetInspiraAvanceUseCase(get(), get(), get()) }
    factory { GetInspiraCondicionesLeyendaUseCase(get(), get(), get(), get()) }
    factory { GetInspiraCondicionesUseCase(get(), get(), get()) }
    factory { GetInspiraPreferencesUseCase(get(), get(), get()) }
    factory { GetInspiraRankingUseCase(get(), get(), get()) }
}
