package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.datos.di

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.datos.ObtenerNombrePersonaUseCase
import org.koin.dsl.module

internal val datosModule = module {
    factory { ObtenerNombrePersonaUseCase(get()) }
}
