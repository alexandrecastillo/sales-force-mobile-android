package biz.belcorp.salesforce.modules.postulants.core.data.repository.sync.di

import biz.belcorp.salesforce.modules.postulants.core.data.repository.sync.SyncPostulantesCloudStore
import biz.belcorp.salesforce.modules.postulants.core.data.repository.sync.SyncPostulantesDataRepository
import biz.belcorp.salesforce.modules.postulants.core.data.repository.sync.SyncPostulantesDataStore
import biz.belcorp.salesforce.modules.postulants.core.domain.repository.sync.SyncPostulantesRepository
import org.koin.dsl.module


internal val syncPostulantesModule = module {

    factory { SyncPostulantesCloudStore(get(), get()) }
    factory { SyncPostulantesDataStore() }

    factory<SyncPostulantesRepository> { SyncPostulantesDataRepository(get(), get(), get()) }

}
