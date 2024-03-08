package biz.belcorp.salesforce.modules.digital.core.domain.di

import biz.belcorp.salesforce.modules.digital.core.domain.usecases.GetDigitalInfoUseCase
import biz.belcorp.salesforce.modules.digital.core.domain.usecases.SyncDigitalInfoUseCase
import org.koin.dsl.module

val domainModules by lazy {
    listOf(usecasesModule)
}

private val usecasesModule = module {
    factory { GetDigitalInfoUseCase(get(), get(), get()) }
    factory { SyncDigitalInfoUseCase(get(), get()) }
}
