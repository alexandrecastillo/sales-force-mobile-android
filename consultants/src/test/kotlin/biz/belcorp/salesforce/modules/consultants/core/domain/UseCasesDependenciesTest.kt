package biz.belcorp.salesforce.modules.consultants.core.domain

import biz.belcorp.salesforce.modules.consultants.base.BaseDependenciesTest
import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.amount.FetchOrdersAmountUseCase
import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.consultora.ConsultoraUseCase
import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.filtros.ObtenerFiltrosBusquedaUseCase
import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.filtros.SeccionesUseCase
import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.sync.SyncUseCase
import biz.belcorp.salesforce.modules.consultants.core.domain.usecases.unified.GetConsultantsUseCase
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test
import org.koin.core.get


class UseCasesDependenciesTest : BaseDependenciesTest() {

    @Test
    fun `resolviendo dependencias para ConsultoraUseCase`() {
        get<ConsultoraUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ObtenerFiltrosBusquedaUseCase`() {
        get<ObtenerFiltrosBusquedaUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para SeccionesUseCase`() {
        get<SeccionesUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para SyncUseCase`() {
        get<SyncUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para FetchOrdersAmountUseCase`() {
        get<FetchOrdersAmountUseCase>().shouldNotBeNull()
    }

    @Test
    fun `solving test for GetConsultantsUseCase`() {
        get<GetConsultantsUseCase>().shouldNotBeNull()
    }

}
