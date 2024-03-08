package biz.belcorp.salesforce.modules.postulants.features.dependencies

import biz.belcorp.salesforce.core.utils.get
import biz.belcorp.salesforce.modules.postulants.base.BaseDependenciesTest
import biz.belcorp.salesforce.modules.postulants.core.domain.usecases.sync.SyncPostulantesUseCase
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.UnetePrePresenter
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.UnetePresenter
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso1.UnetePaso1Presenter
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso2.UnetePaso2Presenter
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso3.UnetePaso3Presenter
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso4.UnetePaso4Presenter
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.postulantes.paso5.UnetePaso5Presenter
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.prepostulantes.paso1.UnetePrePaso1Presenter
import biz.belcorp.salesforce.modules.postulants.features.formulario.formulario.prepostulantes.paso2.UnetePrePaso2Presenter
import biz.belcorp.salesforce.modules.postulants.features.indicador.IndicadorPresenter
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.listado.ConsolidadoPresenter
import biz.belcorp.salesforce.modules.postulants.features.indicador.components.resumen.ResumenConsolidadoPresenter

import org.junit.Test
import kotlin.test.assertNotNull


class PresentersDependenciesTest : BaseDependenciesTest() {

    @Test
    fun `resolviendo dependencias para IndicadorPresenter`() {
        assertNotNull(get<IndicadorPresenter>())
    }

    @Test
    fun `resolviendo dependencias para UnetePresenter`() {
        assertNotNull(get<UnetePresenter>())
    }

    @Test
    fun `resolviendo dependencias para ResumenConsolidadoPresenter`() {
        assertNotNull(get<ResumenConsolidadoPresenter>())
    }

    @Test
    fun `resolviendo dependencias para ConsolidadoPresenter`() {
        assertNotNull(get<ConsolidadoPresenter>())
    }

    @Test
    fun `resolviendo dependencias para UnetePrePresenter`() {
        assertNotNull(get<UnetePrePresenter>())
    }

    @Test
    fun `resolviendo dependencias para UnetePaso1Presenter`() {
        assertNotNull(get<UnetePaso1Presenter>())
    }

    @Test
    fun `resolviendo dependencias para UnetePaso2Presenter`() {
        assertNotNull(get<UnetePaso2Presenter>())
    }

    @Test
    fun `resolviendo dependencias para UnetePaso3Presenter`() {
        assertNotNull(get<UnetePaso3Presenter>())
    }

    @Test
    fun `resolviendo dependencias para UnetePaso4Presenter`() {
        assertNotNull(get<UnetePaso4Presenter>())
    }

    @Test
    fun `resolviendo dependencias para UnetePaso5Presenter`() {
        assertNotNull(get<UnetePaso5Presenter>())
    }

    @Test
    fun `resolviendo dependencias para UnetePrePaso1Presenter`() {
        assertNotNull(get<UnetePrePaso1Presenter>())
    }

    @Test
    fun `resolviendo dependencias para UnetePrePaso2Presenter`() {
        assertNotNull(get<UnetePrePaso2Presenter>())
    }


    @Test
    fun `resolviendo dependencias para syncPostulantesUseCase`() {
        assertNotNull(get<SyncPostulantesUseCase>())
    }
}
