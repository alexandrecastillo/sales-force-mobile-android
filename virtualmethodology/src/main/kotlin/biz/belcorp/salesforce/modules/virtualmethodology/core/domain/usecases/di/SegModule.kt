package biz.belcorp.salesforce.modules.virtualmethodology.core.domain.usecases.di

import biz.belcorp.salesforce.modules.virtualmethodology.core.domain.usecases.GetGroupSegUseCase
import biz.belcorp.salesforce.modules.virtualmethodology.core.domain.usecases.SyncGroupSegUseCase
import org.koin.dsl.module

val segModule = module {
    factory { GetGroupSegUseCase(get()) }
    factory { SyncGroupSegUseCase(get(), get()) }
}
