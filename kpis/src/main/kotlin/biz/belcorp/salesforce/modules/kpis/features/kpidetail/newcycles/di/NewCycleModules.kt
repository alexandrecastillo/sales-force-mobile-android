package biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.di

import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.NewCycleTextResolver
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.creator.NewCycleGridCreator
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.mapper.NewCycleGridMapper
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.mapper.NewCycleIndicatorMapper
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.view.NewCycleIndicatorViewModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.view.detail.multiprofile.consolidated.grid.NewCycleGridViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val newCycleModule = module {
    factory { NewCycleTextResolver(get()) }
    factory { NewCycleIndicatorMapper(get()) }
    factory { NewCycleGridCreator(get()) }
    factory { NewCycleGridMapper(get(), get()) }
    viewModel {
        NewCycleIndicatorViewModel(
            get(),
            get()
        )
    }
    viewModel { NewCycleGridViewModel(get(), get(), get(), get()) }
}
