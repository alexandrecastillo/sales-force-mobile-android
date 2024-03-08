package biz.belcorp.salesforce.modules.postulants.core.domain.usecases.sync.di

import biz.belcorp.salesforce.modules.postulants.core.domain.usecases.sync.SyncPostulantesUseCase
import org.koin.dsl.module

internal val syncPostulantesModule = module {

    factory { SyncPostulantesUseCase(get(), get(), get()) }

}
