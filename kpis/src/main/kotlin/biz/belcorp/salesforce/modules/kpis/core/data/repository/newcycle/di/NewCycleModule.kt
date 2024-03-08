package biz.belcorp.salesforce.modules.kpis.core.data.repository.newcycle.di

import biz.belcorp.salesforce.modules.kpis.core.data.repository.newcycle.NewCycleDataRepository
import biz.belcorp.salesforce.modules.kpis.core.data.repository.newcycle.cloud.NewCycleCloudStore
import biz.belcorp.salesforce.modules.kpis.core.data.repository.newcycle.data.NewCycleDataStore
import biz.belcorp.salesforce.modules.kpis.core.data.repository.newcycle.mapper.NewCycleMapper
import biz.belcorp.salesforce.modules.kpis.core.domain.repository.NewCycleRepository
import org.koin.dsl.module

val newcycleModule = module {

    factory { NewCycleCloudStore(get(), get()) }
    factory { NewCycleDataStore() }
    factory { NewCycleMapper() }

    factory<NewCycleRepository> { NewCycleDataRepository(get(), get(), get(), get()) }

}
