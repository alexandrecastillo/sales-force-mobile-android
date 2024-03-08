package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.kinesis.di

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.kinesis.KinesisManagerCloudDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.kinesis.KinesisManagerDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.kinesis.KinesisManagerRepository
import org.koin.dsl.module

internal val kinesisModule = module {
    factory { KinesisManagerCloudDataStore(get(), get()) }
    factory<KinesisManagerRepository> { KinesisManagerDataRepository(get()) }
}
