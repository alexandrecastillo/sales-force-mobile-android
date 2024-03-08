package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.di

import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.DreamDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.cloud.DreamCloudDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.data.DreamLocalDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream.mapper.DreamMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.dream.DreamRepository
import org.koin.dsl.module

internal val dreamModule = module {
    factory { DreamLocalDataStore() }
    factory { DreamCloudDataStore(get(), get()) }
    factory { DreamMapper() }
    factory<DreamRepository> { DreamDataRepository(get(), get(), get()) }
}
