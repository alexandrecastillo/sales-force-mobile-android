package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.sync.di

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.plan.data.PlanRddDbDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.sync.cloud.SyncCloudStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.sync.SyncDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.sync.SyncDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.sync.UploadVisitasDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.sync.SyncRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.sync.UploadVisitasRepository
import org.koin.dsl.module

internal val syncModule = module {
    factory<UploadVisitasRepository> { UploadVisitasDataRepository(get(), get(), get(), get(), get(), get()) }
    factory<SyncRepository> { SyncDataRepository(get(), get(), get(), get()) }
    factory { SyncDataStore(get(), get()) }
    factory { SyncCloudStore(get(), get()) }
    factory { PlanRddDbDataStore() }
}
