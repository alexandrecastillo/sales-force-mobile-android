package biz.belcorp.salesforce.modules.kpis.core.domain.dependencies

import biz.belcorp.salesforce.core.utils.get
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.KpiDetailParamsUseCase
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.capitalization.CapitalizationKpiUseCase
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.collection.GetGainUseCase
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.dashboard.KpiDashboardUseCase
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.newcycle.GetNewCycleIndicatorUseCase
import biz.belcorp.salesforce.modules.kpis.core.domain.usecases.saleorders.SaleOrdersUseCase
import biz.belcorp.salesforce.modules.kpis.features.base.BaseDependenciesTest
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test

class UseCasesDependenciesTest : BaseDependenciesTest() {

    @Test
    fun `resolviendo dependencias para SalesOrdersUseCase`() {
        get<SaleOrdersUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para CollectionUseCase`() {
        get<GetGainUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para NewCycleUseCase`() {
        get<GetNewCycleIndicatorUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para CapitalizationKpiUseCase`() {
        get<CapitalizationKpiUseCase>().shouldNotBeNull()
    }

    @Test
    fun `solving dependencies for KpiDashboardUseCase`() {
        get<KpiDashboardUseCase>().shouldNotBeNull()
    }

    @Test
    fun `solving dependencies for KpiDetailParamsUseCase`() {
        get<KpiDetailParamsUseCase>().shouldNotBeNull()
    }
}
