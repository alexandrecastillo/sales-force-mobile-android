package biz.belcorp.salesforce.modules.kpis.features.feature.dependencies

import biz.belcorp.salesforce.core.utils.get
import biz.belcorp.salesforce.modules.kpis.features.base.BaseDependenciesTest
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.detail.se.sales.CapitalizationKpiDetailSeOnSalesViewModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.view.shared.capitalization.CapitalizationKpiOnBillingViewModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.detail.se.GainDetailSeViewModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.detailbutton.DetailButtonViewModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.view.NewCycleIndicatorViewModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.detail.multiprofile.SaleOrdersDetailViewModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.detail.se.SaleOrdersDetailSeViewModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.KpiDetailViewModel
import biz.belcorp.salesforce.modules.kpis.features.kpis.KpiDashboardViewModel
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test

class ViewModelsDependenciesTest : BaseDependenciesTest() {

    @Test
    fun `resolver dependencies for SaleOrdersViewModel`() {
        get<KpiDetailViewModel>().shouldNotBeNull()
    }

    @Test
    fun `resolver dependencies for SaleOrdersDetailSeViewModel`() {
        get<SaleOrdersDetailSeViewModel>().shouldNotBeNull()
    }

    @Test
    fun `resolver dependencies for KpiDashboardViewModel`() {
        get<KpiDashboardViewModel>().shouldNotBeNull()
    }

    @Test
    fun `resolver dependencies for Retention KPI SalesPeriodViewModel`() {
        get<CapitalizationKpiDetailSeOnSalesViewModel>().shouldNotBeNull()
    }

    @Test
    fun `resolver dependencies for Retention KPI BillingPeriodViewModel`() {
        get<CapitalizationKpiOnBillingViewModel>().shouldNotBeNull()
    }

    @Test
    fun `resolver dependencies for SaleOrdersDetailViewModel`() {
        get<SaleOrdersDetailViewModel>().shouldNotBeNull()
    }

    @Test
    fun `resolver dependencies for NewCycleIndicatorViewModel`() {
        get<NewCycleIndicatorViewModel>().shouldNotBeNull()
    }

    @Test
    fun `resolver dependencies for DetailButtonSeViewModel`() {
        get<DetailButtonViewModel>().shouldNotBeNull()
    }

    @Test
    fun `resolver dependencies for GainDetailSeViewModel`() {
        get<GainDetailSeViewModel>().shouldNotBeNull()
    }
}
