package biz.belcorp.salesforce.modules.kpis.features.kpidetail.detailbutton.di

import biz.belcorp.salesforce.modules.kpis.features.kpidetail.detailbutton.DetailButtonTextResolver
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.detailbutton.DetailButtonViewModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.detailbutton.mapper.DetailButtonMapper
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val detailButtonModule = module {
    factory { DetailButtonTextResolver(get()) }
    factory { DetailButtonMapper(get()) }
    viewModel { DetailButtonViewModel(get(), get(), get()) }
}
