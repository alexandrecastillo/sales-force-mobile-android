package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.comportamientos.di

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.comportamientos.ObtenerComportamientosDetallePorcentajeUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.comportamientos.reconocer.ReconocerComportamientosUseCase
import org.koin.dsl.module

internal val comportamientoModule = module {
    factory { ObtenerComportamientosDetallePorcentajeUseCase(get(), get(), get(), get(), get()) }
    factory { ReconocerComportamientosUseCase(get(), get(), get(), get(), get(), get()) }
}
