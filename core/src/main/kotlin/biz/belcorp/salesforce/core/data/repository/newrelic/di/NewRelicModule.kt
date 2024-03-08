package biz.belcorp.salesforce.core.data.repository.newrelic.di

import biz.belcorp.salesforce.core.data.repository.newrelic.NewRelicDataRepository
import biz.belcorp.salesforce.core.domain.repository.newrelic.NewRelicRepository
import org.koin.dsl.module

val newRelicModule = module {
    factory<NewRelicRepository> { NewRelicDataRepository(get(), get()) }
}
