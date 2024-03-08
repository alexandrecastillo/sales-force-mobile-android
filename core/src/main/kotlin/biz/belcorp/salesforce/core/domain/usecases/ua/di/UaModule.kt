package biz.belcorp.salesforce.core.domain.usecases.ua.di

import biz.belcorp.salesforce.core.domain.usecases.ua.UaInfoUseCase
import org.koin.dsl.module

val uaModule = module {
    factory { UaInfoUseCase(get(), get(), get(), get(), get()) }
}
