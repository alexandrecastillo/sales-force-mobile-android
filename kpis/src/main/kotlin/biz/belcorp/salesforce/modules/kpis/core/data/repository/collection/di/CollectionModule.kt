package biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.di

import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.CollectionDataRepository
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.ProfitDataRepository
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.RetentionDataRepository
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.cloud.CollectionCloudStore
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.cloud.ProfitCloudStore
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.cloud.RetentionCloudStore
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.data.CollectionDataStore
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.data.ProfitDataStore
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.data.RetentionDataStore
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.mapper.CollectionMapper
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.mapper.ProfitMapper
import biz.belcorp.salesforce.modules.kpis.core.data.repository.collection.mapper.RetentionMapper
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.CollectionRepository
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.ProfitRepository
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.RetentionRepository
import org.koin.dsl.module

internal val collectionModule = module {
    factory { ProfitCloudStore(get(), get()) }
    factory { CollectionCloudStore(get(), get()) }
    factory { ProfitDataStore() }
    factory { CollectionDataStore() }
    factory { RetentionDataStore() }
    factory { RetentionCloudStore(get(), get()) }
    factory { RetentionMapper() }
    factory { ProfitMapper() }

    factory { CollectionMapper() }

    factory<ProfitRepository> { ProfitDataRepository(get(), get(), get(), get()) }
    factory<CollectionRepository> { CollectionDataRepository(get(), get(), get(), get(), get()) }
    factory<RetentionRepository> { RetentionDataRepository(get(), get(), get(), get()) }

}
