package biz.belcorp.salesforce.modules.consultants.core.domain.usecases.amount.di

import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.amount.FetchOrdersAmountUseCase
import org.koin.dsl.module

val amountsModule = module {
    factory { FetchOrdersAmountUseCase(get(), get()) }
}
