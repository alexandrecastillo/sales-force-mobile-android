package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.desarrollo.di

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.desarrollo.RecuperarTituloDesarrolloUaUseCase
import org.koin.dsl.module

internal val desarrolloModule = module {
    factory { RecuperarTituloDesarrolloUaUseCase(get(), get(), get(), get()) }
}
