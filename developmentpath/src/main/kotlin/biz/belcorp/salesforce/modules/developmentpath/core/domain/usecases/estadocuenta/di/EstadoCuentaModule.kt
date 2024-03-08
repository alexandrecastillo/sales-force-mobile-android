package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.estadocuenta.di

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.estadocuenta.ObtenerEstadoDeCuentaUseCase
import org.koin.dsl.module

internal val estadoCuentaModule = module {
    factory { ObtenerEstadoDeCuentaUseCase(get(), get()) }
}
