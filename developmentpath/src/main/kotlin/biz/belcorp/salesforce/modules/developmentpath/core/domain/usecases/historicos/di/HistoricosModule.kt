package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.historicos.di

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.historicos.HistoricaRegionBaseUseCase
import org.koin.dsl.module

internal val historicosModule = module {
    factory { HistoricaRegionBaseUseCase(get(), get(), get(), get()) }
}
