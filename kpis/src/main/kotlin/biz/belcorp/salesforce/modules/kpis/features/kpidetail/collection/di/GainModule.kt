package biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.di

import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.CollectionTextResolver
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile.CollectionDetailViewModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile.items.consolidated.grid.GainConsolidatedGridViewModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile.items.consolidated.grid.GainConsolidatedMapper
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile.items.consolidated.grid.creator.GainGridCreator
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.multiprofile.mapper.GainUaMapper
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.se.GainDetailSeViewModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.se.mapper.CollectionDetailMapper
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.header.multiprofile.GainHeaderMapper
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.header.multiprofile.GainHeaderViewModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.viewmodel.UaDetailViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val gainModule = module {

    factory { GainGridCreator() }

    factory { CollectionDetailMapper(get()) }
    factory { GainHeaderMapper(get()) }
    factory { GainConsolidatedMapper(get(), get()) }

    factory { GainUaMapper() }

    viewModel { GainDetailSeViewModel(get(), get(), get(), get(), get()) }

    factory { CollectionTextResolver(get()) }

    viewModel { GainHeaderViewModel(get(), get()) }

    viewModel { CollectionDetailViewModel(get()) }

    viewModel {
        GainConsolidatedGridViewModel(
            get(),
            get()
        )
    }

    viewModel {
        UaDetailViewModel(
            get(),
            get(),
            get()
        )
    }
}
