package biz.belcorp.salesforce.modules.kpis.core.data.repository.bonification.di

import biz.belcorp.salesforce.modules.kpis.core.data.repository.bonification.BonificationDataRepository
import biz.belcorp.salesforce.modules.kpis.core.data.repository.bonification.cloud.BonificationCloudStore
import biz.belcorp.salesforce.modules.kpis.core.data.repository.bonification.data.BonificationDataStore
import biz.belcorp.salesforce.modules.kpis.core.data.repository.bonification.mapper.BonificationMapper
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.BonificationRepository
import org.koin.dsl.module

val bonificationModule = module {

    factory { BonificationMapper() }
    factory { BonificationCloudStore(get(), get()) }
    factory { BonificationDataStore() }

    factory<BonificationRepository> { BonificationDataRepository(get(), get(), get()) }

}
