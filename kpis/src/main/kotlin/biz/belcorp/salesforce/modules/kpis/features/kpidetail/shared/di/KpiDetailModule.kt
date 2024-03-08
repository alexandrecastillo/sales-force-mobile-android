package biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.di

import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.KpiDetailMapper
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.KpiDetailViewModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.KpiGridSelectorMapper
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.KpiGridSelectorTextResolver
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val kpiDetailModule = module {
    factory { KpiDetailMapper() }
    factory { KpiGridSelectorMapper(get()) }
    factory { KpiGridSelectorTextResolver(get()) }
    viewModel { KpiDetailViewModel(get(), get()) }
}
