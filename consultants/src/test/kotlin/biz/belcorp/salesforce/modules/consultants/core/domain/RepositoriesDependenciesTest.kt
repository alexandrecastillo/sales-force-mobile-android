package biz.belcorp.salesforce.modules.consultants.core.domain


import biz.belcorp.salesforce.modules.consultants.base.BaseDependenciesTest
import biz.belcorp.salesforce.modules.consultants.core.domain.repository.amount.OrdersAmountRepository
import biz.belcorp.salesforce.modules.consultants.core.domain.repository.consultoras.ConsultoraRepository
import biz.belcorp.salesforce.modules.consultants.core.domain.repository.filtros.*
import biz.belcorp.salesforce.modules.consultants.core.domain.repository.secciones.SeccionRepository
import biz.belcorp.salesforce.modules.consultants.core.domain.repository.sync.SyncRepository
import biz.belcorp.salesforce.modules.consultants.core.domain.repository.unified.ConsultantsRepository
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test
import org.koin.core.get


class RepositoriesDependenciesTest : BaseDependenciesTest() {

    @Test
    fun `resolviendo dependencias para ConsultoraRepository`() {
        get<ConsultoraRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para TipoEstadoRepository`() {
        get<TipoEstadoRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para TipoPedidoRepository`() {
        get<TipoPedidoRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para TipoSaldoRepository`() {
        get<TipoSaldoRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para TipoSegmentoRepository`() {
        get<TipoSegmentoRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para SeccionRepository`() {
        get<SeccionRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para SyncRepository`() {
        get<SyncRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para OrdersAmountRepository`() {
        get<OrdersAmountRepository>().shouldNotBeNull()
    }

    @Test
    fun `solving test for ConsultantsRepository`() {
        get<ConsultantsRepository>().shouldNotBeNull()
    }

}
