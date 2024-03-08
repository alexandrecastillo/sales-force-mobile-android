package biz.belcorp.salesforce.modules.consultants.core.domain.usecases.consultora.di

import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.consultora.ConsultoraUseCase
import org.koin.dsl.module

val consultoraModule = module {

    factory { ConsultoraUseCase(get(), get(), get(), get()) }

}
