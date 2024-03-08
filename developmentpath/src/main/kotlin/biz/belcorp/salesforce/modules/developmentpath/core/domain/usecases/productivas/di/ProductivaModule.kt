package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.productivas.di

import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.productivas.ProductivasU3CUseCase
import org.koin.dsl.module

internal val productivasModule = module {
    factory { ProductivasU3CUseCase(get(), get(), get(), get()) }
}
