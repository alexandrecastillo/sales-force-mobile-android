package biz.belcorp.salesforce.modules.auth.core.data.repository.auth.di

import biz.belcorp.salesforce.core.data.repository.auth.AuthTokenRenewer
import biz.belcorp.salesforce.modules.auth.core.data.repository.auth.AuthCloudStore
import biz.belcorp.salesforce.modules.auth.core.data.repository.auth.AuthDataRepository
import biz.belcorp.salesforce.modules.auth.core.data.repository.auth.AuthLegacyCloudStore
import biz.belcorp.salesforce.modules.auth.core.data.repository.auth.AuthTokenCloudStore
import biz.belcorp.salesforce.modules.auth.core.domain.repository.AuthRepository
import org.koin.dsl.module


val authModule = module {

    factory { AuthCloudStore(get()) }
    factory { AuthLegacyCloudStore(get()) }

    factory<AuthRepository> { AuthDataRepository(get(), get(), get(), get(), get(), get(), get()) }

    single<AuthTokenRenewer> { AuthTokenCloudStore(get(), get(), get(), get(), get()) }

}
