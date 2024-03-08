package biz.belcorp.salesforce.modules.consultants.core.domain.usecases.sync.di

import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.sync.SyncConsultantsUseCase
import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.sync.SyncUseCase
import org.koin.dsl.module


val syncModule = module {

    factory { SyncUseCase(get(), get()) }
    factory { SyncConsultantsUseCase(get(), get(), get()) }

}
