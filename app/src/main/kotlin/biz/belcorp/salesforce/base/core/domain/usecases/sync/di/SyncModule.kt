package biz.belcorp.salesforce.base.core.domain.usecases.sync.di

import biz.belcorp.salesforce.base.core.domain.usecases.sync.SyncUseCase
import org.koin.dsl.module

internal val syncModule = module {

    factory { SyncUseCase(get(), get(), get(), get(), get(), get()) }

}
