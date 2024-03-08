package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tips.di

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tips.ObtenerTipsVisitaUseCase
import org.koin.dsl.module

internal val tipsModule = module {
    factory { ObtenerTipsVisitaUseCase(get(), get(), get()) }
}
