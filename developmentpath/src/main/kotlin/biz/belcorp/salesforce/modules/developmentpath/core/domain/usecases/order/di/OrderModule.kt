package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.order.di

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.order.GetOrdersUseCase
import org.koin.dsl.module

internal val orderModule = module {
    factory { GetOrdersUseCase(get(), get(), get()) }
}
