package biz.belcorp.salesforce.modules.orders.core.domain.dependencies

import biz.belcorp.salesforce.modules.orders.base.BaseDependenciesTest
import biz.belcorp.salesforce.modules.orders.core.domain.repository.FiltersRepository
import biz.belcorp.salesforce.modules.orders.core.domain.repository.PedidosWebRepository
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test
import org.koin.core.get


class RepositoriesDependenciesTest : BaseDependenciesTest() {

    @Test
    fun `resolviendo dependencias para FiltersRepository`() {
        get<FiltersRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para PedidosWebRepository`() {
        get<PedidosWebRepository>().shouldNotBeNull()
    }

}
