package biz.belcorp.salesforce.modules.brightpath.features.di

import biz.belcorp.salesforce.modules.brightpath.features.container.IndicatorDetailPresenter
import biz.belcorp.salesforce.modules.brightpath.features.container.consultants.ConsultantsDetailPresenter
import biz.belcorp.salesforce.modules.brightpath.features.container.detail.BrightPathIndicatorDetailPresenter
import biz.belcorp.salesforce.modules.brightpath.features.container.detail.filter.FilterConstancyPresenter
import biz.belcorp.salesforce.modules.brightpath.features.drilldown.GridBrightPathDetailKpiPresenter
import biz.belcorp.salesforce.modules.brightpath.features.drilldown.utils.GridBuilder
import biz.belcorp.salesforce.modules.brightpath.features.header.BrightPathHeaderKpiPresenter
import biz.belcorp.salesforce.modules.brightpath.features.ua.UASegmentPresenter
import org.koin.dsl.module

val featureModules = module {

    factory { ConsultantsDetailPresenter(get(), get()) }
    factory { FilterConstancyPresenter() }
    factory { BrightPathIndicatorDetailPresenter(get()) }
    factory { IndicatorDetailPresenter(get(), get(), get(), get(), get()) }
    factory { GridBuilder(get()) }
    factory { GridBrightPathDetailKpiPresenter(get(), get(), get(), get()) }
    factory { BrightPathHeaderKpiPresenter(get()) }
    factory { UASegmentPresenter(get(), get()) }

}
