package biz.belcorp.salesforce.modules.consultants.core.domain.usecases.filtros.di

import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.filtros.ObtenerFiltrosBusquedaUseCase
import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.filtros.SeccionesUseCase
import org.koin.dsl.module

val filtrosModule = module {

    factory {
        ObtenerFiltrosBusquedaUseCase(
            get(),
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }
    factory { SeccionesUseCase(get(), get(), get()) }

}
