package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dashboard.di

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.dashboard.cloud.DashboardCloudDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.dashboard.data.DashboardLocalDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.dashboard.DashboardDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.online.DashboardRepository
import org.koin.dsl.module

internal val dashboardModule = module {
    factory { DashboardCloudDataStore(get(), get()) }
    factory { DashboardLocalDataStore() }
    factory<DashboardRepository> { DashboardDataRepository(database = get(), cloud = get()) }
}
