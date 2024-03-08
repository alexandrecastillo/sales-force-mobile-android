package biz.belcorp.salesforce.core.data.repository.configuration.di

import biz.belcorp.salesforce.core.data.repository.configuration.ConfigurationDataRepository
import biz.belcorp.salesforce.core.data.repository.configuration.cloud.ConfigurationCloudStore
import biz.belcorp.salesforce.core.data.repository.configuration.data.ConfigurationDataStore
import biz.belcorp.salesforce.core.data.repository.configuration.mappers.ConfigurationMapper
import biz.belcorp.salesforce.core.domain.repository.configuration.ConfigurationRepository
import org.koin.dsl.module

val configurationModule = module {

    factory { ConfigurationCloudStore(get(), get()) }
    factory { ConfigurationDataStore() }
    factory { ConfigurationMapper() }

    factory<ConfigurationRepository> {
        ConfigurationDataRepository(
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }

}
