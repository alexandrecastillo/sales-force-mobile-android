package biz.belcorp.salesforce.modules.kpis.core.domain.usecases.di

import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.saleorders.GridConsolidatedUseCase
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.saleorders.SaleOrdersDetailUseCase
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.saleorders.SaleOrdersUseCase
import org.koin.dsl.module

val saleOrdersModule = module {
    factory { SaleOrdersDetailUseCase(get(), get()) }
    factory { SaleOrdersUseCase(get(), get(), get(), get(), get()) }
    factory { GridConsolidatedUseCase(get(), get(), get(), get(), get(), get()) }
}
