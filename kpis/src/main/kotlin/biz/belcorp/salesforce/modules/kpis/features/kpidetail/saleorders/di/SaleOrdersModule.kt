package biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.di

import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.SaleOrdersTextResolver
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.mapper.GridConsolidatedMapper
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.mapper.SaleOrdersDetailMapper
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.mapper.SaleOrdersDetailTextResolver
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.detail.multiprofile.SaleOrdersDetailViewModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.detail.multiprofile.items.consolidated.grid.ConsolidatedGridViewModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.detail.se.SaleOrdersDetailSeViewModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.detail.se.mapper.SaleOrderSeMapper
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.header.multiprofile.SaleOrdersHeaderViewModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.header.multiprofile.mapper.SaleOrderMapper
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val salesOrdersModule = module {

    factory { SaleOrderSeMapper(get()) }
    factory { SaleOrderMapper(get()) }
    factory { SaleOrdersDetailMapper(get()) }
    factory { GridConsolidatedMapper(get()) }

    viewModel { SaleOrdersDetailSeViewModel(get(), get()) }
    viewModel { SaleOrdersDetailViewModel(get(), get()) }
    viewModel { SaleOrdersHeaderViewModel(get(), get()) }
    viewModel { ConsolidatedGridViewModel(get(), get()) }

    factory { SaleOrdersTextResolver(get()) }
    factory { SaleOrdersDetailTextResolver(get()) }
}
