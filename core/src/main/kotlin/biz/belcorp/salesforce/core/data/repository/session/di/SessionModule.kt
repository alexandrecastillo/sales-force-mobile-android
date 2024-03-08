package biz.belcorp.salesforce.core.data.repository.session.di

import biz.belcorp.salesforce.core.data.repository.saleforcestatus.cloud.SaleForceStatusCloudStore
import biz.belcorp.salesforce.core.data.repository.session.SessionDataRepository
import biz.belcorp.salesforce.core.data.repository.session.cloud.ProfileCloudStore
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository
import org.koin.dsl.module

internal val sessionModule = module {
    factory { ProfileCloudStore(get(), get()) }
    factory { SaleForceStatusCloudStore(get())}
    factory<SessionRepository> { SessionDataRepository(get(), get(), get(), get(), get(), get()) }
}
