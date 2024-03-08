package biz.belcorp.salesforce.modules.kpis.core.domain.dependencies

import biz.belcorp.salesforce.modules.kpis.core.domain.repository.*
import biz.belcorp.salesforce.modules.kpis.features.base.BaseDependenciesTest
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test
import org.koin.core.get

class
RepositoriesDependenciesTest : BaseDependenciesTest() {

    @Test
    fun `resolviendo dependencias para SalesOrdersRepository`() {
        get<SaleOrdersRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para NewCycleRepository`() {
        get<NewCycleRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para CollectionRepository`() {
        get<CollectionRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ProfitRepository`() {
        get<ProfitRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para CapitalizationRepository`() {
        get<CapitalizationRepository>().shouldNotBeNull()
    }

}
