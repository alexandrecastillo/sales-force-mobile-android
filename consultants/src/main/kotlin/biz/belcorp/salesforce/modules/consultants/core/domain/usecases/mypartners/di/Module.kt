package biz.belcorp.salesforce.modules.consultants.core.domain.usecases.mypartners.di

import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.mypartners.MyPartnersStoreUseCase
import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.mypartners.MyPartnersUseCase
import org.koin.dsl.module

val myPartnersModule = module {
    factory { MyPartnersUseCase(get(), get(), get()) }
    factory { MyPartnersStoreUseCase(get()) }
}
