package biz.belcorp.salesforce.modules.postulants.core.domain.dependencies

import biz.belcorp.salesforce.core.domain.usecases.campania.ObtenerCampaniasUseCase
import biz.belcorp.salesforce.core.domain.usecases.session.ObtenerSesionUseCase
import biz.belcorp.salesforce.modules.postulants.base.BaseDependenciesTest
import biz.belcorp.salesforce.modules.postulants.core.domain.usecases.*
import biz.belcorp.salesforce.modules.postulants.core.domain.usecases.sync.SyncPostulantesUseCase
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test
import org.koin.core.get
import kotlin.test.assertNotNull


class UseCasesDependenciesTest : BaseDependenciesTest() {

    @Test
    fun `resolviendo dependencias para MapsUseCase`() {
        get<MapsUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ObtenerNombreConsultoraRecomendanteUseCase`() {
        get<ObtenerNombreConsultoraRecomendanteUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ParametroUneteUseCase`() {
        get<ParametroUneteUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para PostulantesUseCase`() {
        get<PostulantesUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para TablaLogicaUseCase`() {
        get<TablaLogicaUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para TipoMetaUseCase`() {
        get<TipoMetaUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para UneteConfiguracionUseCase`() {
        get<UneteConfiguracionUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ValidarBloqueosUseCase`() {
        get<ValidarBloqueosUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para SyncPostulantesUseCase`() {
        get<SyncPostulantesUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para postulantesUseCase`() {
        assertNotNull(get<PostulantesUseCase>())
    }

    @Test
    fun `resolviendo dependencias para ObtenerCampaniasUseCase`() {
        assertNotNull(get<ObtenerCampaniasUseCase>())
    }

    @Test
    fun `resolviendo dependencias para IndicadorUseCase`() {
        assertNotNull(get<IndicadorUseCase>())
    }

    @Test
    fun `resolviendo dependencias para ObtenerSesionUseCase`() {
        assertNotNull(get<ObtenerSesionUseCase>())
    }

    @Test
    fun `resolviendo dependencias para syncPostulantesUseCase`() {
        assertNotNull(get<SyncPostulantesUseCase>())
    }

}
