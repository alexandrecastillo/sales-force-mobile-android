package biz.belcorp.salesforce.core.data.repository.socialbonus.di

import biz.belcorp.salesforce.core.data.repository.socialbonus.SocialBonusDBDataStore
import biz.belcorp.salesforce.core.data.repository.socialbonus.SocialBonusDataRepository
import biz.belcorp.salesforce.core.data.repository.socialbonus.SocialBonusMapper
import biz.belcorp.salesforce.core.domain.repository.socialbonus.SocialBonusRepository
import org.koin.dsl.module

internal val socialBonusModule = module {
    factory { SocialBonusDBDataStore() }
    factory<SocialBonusRepository> { SocialBonusDataRepository(get(), get()) }
    factory { SocialBonusMapper() }
}
