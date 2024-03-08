package biz.belcorp.salesforce.core.data.repository.campania.di

import biz.belcorp.salesforce.core.data.repository.campania.CampaniasCloudStore
import biz.belcorp.salesforce.core.data.repository.campania.CampaniasDataMapper
import biz.belcorp.salesforce.core.data.repository.campania.CampaniasDataRepository
import biz.belcorp.salesforce.core.data.repository.campania.CampaniasDataStore
import biz.belcorp.salesforce.core.domain.repository.campania.CampaniasRepository
import org.koin.dsl.module


internal val campaniasModule = module {

    factory { CampaniasDataMapper() }

    factory { CampaniasDataStore() }

    factory { CampaniasCloudStore(get(), get()) }

    factory<CampaniasRepository> { CampaniasDataRepository(get(), get(), get(), get()) }

}
