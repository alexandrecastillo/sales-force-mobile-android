package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.cabecera.di

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.cabecera.CabeceraUseCase
import org.koin.dsl.module

internal val cabeceraModule = module {
    factory { CabeceraUseCase(get(), get(), get(), get(), get(), get(), get()) }
}
