package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.ranking.di

import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.ranking.ProfileRankingDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.ranking.cloud.ProfileRankingCloudStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.ranking.data.ProfileRankingDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.ranking.mapper.ProfileRankingMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.ranking.ProfileRankingRepository
import org.koin.dsl.module

internal val rankingModule = module {
    factory { ProfileRankingMapper() }
    factory { ProfileRankingDataStore() }
    factory { ProfileRankingCloudStore(get(), get()) }
    factory<ProfileRankingRepository> { ProfileRankingDataRepository(get(), get(), get(), get()) }
}
