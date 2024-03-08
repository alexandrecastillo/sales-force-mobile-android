package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.flujo.di

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.flujo.FlujoCascadaUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.flujo.FlujoRddUseCase
import org.koin.dsl.module

internal val flujoModule = module {
    factory { FlujoRddUseCase(get(), get(), get(), get()) }
    factory { FlujoCascadaUseCase(get(),get(),get(),get(),get(),get()) }
}
