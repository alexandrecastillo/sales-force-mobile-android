package biz.belcorp.salesforce.modules.digital.core.data.repository.di

import biz.belcorp.salesforce.modules.digital.core.data.repository.digital.DigitalDataRepository
import biz.belcorp.salesforce.modules.digital.core.data.repository.digital.cloud.DigitalCloudStore
import biz.belcorp.salesforce.modules.digital.core.data.repository.digital.data.DigitalDataStore
import biz.belcorp.salesforce.modules.digital.core.data.repository.digital.mappers.DigitalDataMapper
import biz.belcorp.salesforce.modules.digital.core.domain.repository.DigitalRepository
import org.koin.dsl.module

val repositoryModule = module {

    factory { DigitalDataMapper() }
    factory { DigitalDataStore() }
    factory { DigitalCloudStore(get(), get()) }
    factory<DigitalRepository> { DigitalDataRepository(get(), get(), get()) }

}
