package biz.belcorp.salesforce.modules.orders.core.domain.dependencies

import biz.belcorp.salesforce.modules.orders.base.BaseDependenciesTest
import biz.belcorp.salesforce.modules.orders.core.domain.usecases.filters.GetSearchFiltersUseCase
import biz.belcorp.salesforce.modules.orders.core.domain.usecases.orders.BloquearPedidoUseCase
import biz.belcorp.salesforce.modules.orders.core.domain.usecases.orders.BuscarPedidosUseCase
import biz.belcorp.salesforce.modules.orders.core.domain.usecases.orders.CheckLockConfigUseCase
import biz.belcorp.salesforce.modules.orders.core.domain.usecases.orders.DesbloquearPedidoUseCase
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test
import org.koin.core.get


class UseCasesDependenciesTest : BaseDependenciesTest() {

    @Test
    fun `resolviendo dependencias para GetSearchFiltersUseCase`() {
        get<GetSearchFiltersUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para BloquearPedidoUseCase`() {
        get<BloquearPedidoUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para BuscarPedidosUseCase`() {
        get<BuscarPedidosUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para CheckLockConfigUseCase`() {
        get<CheckLockConfigUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para DesbloquearPedidoUseCase`() {
        get<DesbloquearPedidoUseCase>().shouldNotBeNull()
    }

}
