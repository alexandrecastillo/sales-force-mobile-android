package biz.belcorp.salesforce.core.domain.usecases.configuration.di

import biz.belcorp.salesforce.core.domain.usecases.configuration.ConfigurationUseCase
import org.koin.dsl.module

val configurationModule = module {
    factory { ConfigurationUseCase(get(), get()) }
}
