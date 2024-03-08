package biz.belcorp.salesforce.core.domain.usecases.device.di

import biz.belcorp.salesforce.core.domain.usecases.device.UpdateTokenUseCase
import org.koin.dsl.module


internal val deviceModule = module {

    factory { UpdateTokenUseCase(get(), get()) }
}
