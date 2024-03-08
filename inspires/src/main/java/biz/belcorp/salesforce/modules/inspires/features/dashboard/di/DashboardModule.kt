package biz.belcorp.salesforce.modules.inspires.features.dashboard.di

import biz.belcorp.salesforce.modules.inspires.features.dashboard.InspireDashboardPresenter
import biz.belcorp.salesforce.modules.inspires.mapper.InspireModelDataMapper
import org.koin.dsl.module

internal val dashboardModule = module {

    factory { InspireModelDataMapper() }
    factory { InspireDashboardPresenter(get(), get(), get(), get()) }

}
