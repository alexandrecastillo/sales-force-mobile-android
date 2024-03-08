package biz.belcorp.salesforce.base.core.data.repository.options.di

import biz.belcorp.salesforce.base.core.data.repository.options.OptionsDataRepository
import biz.belcorp.salesforce.base.core.data.repository.options.cloud.OptionsCloudStore
import biz.belcorp.salesforce.base.core.data.repository.options.data.OptionsDataStore
import biz.belcorp.salesforce.base.core.data.repository.options.mappers.OptionsMapper
import biz.belcorp.salesforce.base.core.domain.repository.options.OptionsRepository
import org.koin.dsl.module

val optionsModule = module {

    factory { OptionsMapper() }

    factory { OptionsCloudStore(get(), get()) }
    factory { OptionsDataStore() }

    factory<OptionsRepository> { OptionsDataRepository(get(), get(), get()) }

}
