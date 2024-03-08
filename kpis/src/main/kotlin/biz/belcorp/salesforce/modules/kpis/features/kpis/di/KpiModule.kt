package biz.belcorp.salesforce.modules.kpis.features.kpis.di

import biz.belcorp.salesforce.modules.kpis.features.kpis.KpiDashboardTextResolver
import biz.belcorp.salesforce.modules.kpis.features.kpis.KpiDashboardViewModel
import biz.belcorp.salesforce.modules.kpis.features.kpis.mapper.collection.KpiCollectionMapper
import biz.belcorp.salesforce.modules.kpis.features.kpis.mapper.newcycle.KpiNewCycleMapper
import biz.belcorp.salesforce.modules.kpis.features.kpis.mapper.saleorders.KpiSaleOrdersMapper
import biz.belcorp.salesforce.modules.kpis.features.kpis.mapper.KpiDashboardMapper
import biz.belcorp.salesforce.modules.kpis.features.kpis.mapper.capitalization.KpiRetentionCapiMapper
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val kpiModule = module {
    factory { KpiSaleOrdersMapper(get()) }
    factory { KpiCollectionMapper(get()) }
    factory { KpiRetentionCapiMapper(get()) }
    factory { KpiNewCycleMapper(get()) }
    factory { KpiDashboardTextResolver(get()) }
    factory { KpiDashboardMapper(get(), get(), get(), get(), get()) }
    viewModel { KpiDashboardViewModel(get(), get(), get(), get()) }
}
