package biz.belcorp.salesforce.modules.postulants.core.domain.dependencies

import biz.belcorp.salesforce.modules.postulants.base.BaseDependenciesTest
import biz.belcorp.salesforce.modules.postulants.core.domain.repository.*
import biz.belcorp.salesforce.modules.postulants.core.domain.repository.sync.SyncPostulantesRepository
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test
import org.koin.core.get


class RepositoriesDependenciesTest : BaseDependenciesTest() {

    @Test
    fun `resolviendo dependencias para ConsultaCrediticiaRepository`() {
        get<ConsultaCrediticiaRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para MapsRepository`() {
        get<MapsRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ParametroUneteRepository`() {
        get<ParametroUneteRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para PostulantesRepository`() {
        get<PostulantesRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para UneteConfiguracionRepository`() {
        get<UneteConfiguracionRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para TablaLogicaRepository`() {
        get<TablaLogicaRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para TipoMetaRepository`() {
        get<TipoMetaRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para SyncPostulantesRepository`() {
        get<SyncPostulantesRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para IndicadorRepository`() {
        get<IndicadorRepository>().shouldNotBeNull()
    }

}
