package biz.belcorp.salesforce.modules.kpis.core.domain.usecases.di

import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.collection.GetGainPartnerUseCase
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.collection.GetGainUseCase
import org.koin.dsl.module

val collectionModule = module {
    factory { GetGainUseCase(get(), get(), get(), get(), get(), get(), get(), get()) }
    factory { GetGainPartnerUseCase(get(), get(), get(), get(), get(), get(), get(), get()) }
}
