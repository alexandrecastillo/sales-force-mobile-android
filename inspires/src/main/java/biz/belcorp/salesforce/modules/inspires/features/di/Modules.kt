package biz.belcorp.salesforce.modules.inspires.features.di

import biz.belcorp.salesforce.core.utils.listByElementsOf
import biz.belcorp.salesforce.modules.inspires.features.analitycs.AnalyticsInspireViewModel
import biz.belcorp.salesforce.modules.inspires.features.benefits.di.benefitsModule
import biz.belcorp.salesforce.modules.inspires.features.dashboard.di.dashboardModule
import biz.belcorp.salesforce.modules.inspires.features.sync.di.syncModule
import biz.belcorp.salesforce.modules.inspires.features.travel.di.travelModule
import org.koin.core.module.Module
import org.koin.dsl.module

val featureModules by lazy {
    listByElementsOf<Module>(
        analyticsView,
        dashboardModule,
        travelModule,
        benefitsModule,
        syncModule
    )
}

internal val analyticsView = module {

    factory { AnalyticsInspireViewModel(get()) }

}
