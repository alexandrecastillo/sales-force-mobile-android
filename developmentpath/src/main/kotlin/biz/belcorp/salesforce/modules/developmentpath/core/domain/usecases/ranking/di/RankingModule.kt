package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.ranking.di

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.ranking.GetRankingUseCase
import org.koin.dsl.module

internal val rankingModule = module {
    factory { GetRankingUseCase(get(), get(), get(), get()) }
}
