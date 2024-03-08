package biz.belcorp.salesforce.modules.developmentpath.core.domain.sugerencias.di

import biz.belcorp.salesforce.modules.developmentpath.core.domain.sugerencias.RecuperadorSugerencias
import org.koin.dsl.module

internal val sugerenciasModule = module {
    factory { RecuperadorSugerencias() }
}
