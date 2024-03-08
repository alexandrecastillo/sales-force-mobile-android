package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.acuerdos.di

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.acuerdos.*
import org.koin.dsl.module

internal val acuerdosModule = module {
    factory { AcuerdosCampaniaActualUseCase(get(), get(), get(), get()) }
    factory { ConsolidadoAcuerdosUseCase(get(), get(), get(), get(), get(), get()) }
    factory { AcuerdosHistoricosUseCase(get(), get(), get(), get()) }
    factory { CreacionAcuerdosUseCase(get(), get(), get()) }
    factory { ModificarAcuerdosUseCase(get(), get(), get()) }
}
