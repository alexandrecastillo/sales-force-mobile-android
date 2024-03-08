package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.di

import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.DreamBpDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.cloud.DreamBpCloudDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.data.DreamBpLocalDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dream_bp.mapper.DreamBpMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.dream_bp.DreamBpRepository
import org.koin.dsl.module

internal val dreamBpModule = module {
    factory { DreamBpLocalDataStore() }
    factory { DreamBpCloudDataStore(get(), get()) }
    factory { DreamBpMapper() }
    factory<DreamBpRepository> { DreamBpDataRepository(get(), get(), get()) }
}
