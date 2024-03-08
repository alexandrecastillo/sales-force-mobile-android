package biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.di

import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.CapitalizationTextResolver
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.RetentionCapiTextResolver
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.mapper.CapitalizationKpiMapper
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.detail.multiprofile.sales.consolidated.grid.GridCapitalizationKpiViewModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.detail.multiprofile.sales.consolidated.grid.GridCapitalizationMapper
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.detail.se.sales.CapitalizationKpiDetailSeOnSalesViewModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.shared.capitalization.CapitalizationKpiOnBillingViewModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.shared.postulants.PostulantKpiMapper
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.shared.postulants.PostulantKpiTextResolver
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.shared.postulants.PostulantKpiViewModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.KpiGridSelectorViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val retentionCapitalizationModule = module {

    factory { CapitalizationKpiMapper(get()) }
    factory { PostulantKpiMapper(get()) }
    factory { GridCapitalizationMapper(get()) }

    factory { CapitalizationTextResolver(get()) }
    factory { RetentionCapiTextResolver(get()) }
    factory { PostulantKpiTextResolver(get()) }


    viewModel { PostulantKpiViewModel(get(), get()) }
    viewModel { KpiGridSelectorViewModel(get(), get()) }
    viewModel { GridCapitalizationKpiViewModel(get(), get()) }

    viewModel { CapitalizationKpiDetailSeOnSalesViewModel(get(), get()) }

    viewModel {
        CapitalizationKpiOnBillingViewModel(
            get(),
            get(),
            get()
        )
    }

}
