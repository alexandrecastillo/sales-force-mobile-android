package biz.belcorp.salesforce.modules.consultants.feature.dependencies

import biz.belcorp.salesforce.core.utils.get
import biz.belcorp.salesforce.modules.consultants.base.BaseDependenciesTest
import biz.belcorp.salesforce.modules.consultants.features.list.ConsultantsListPresenter
import biz.belcorp.salesforce.modules.consultants.features.list.ConsultantsListView
import biz.belcorp.salesforce.modules.consultants.features.search.BusquedaConsultoraPresenter
import biz.belcorp.salesforce.modules.consultants.features.search.BusquedaConsultoraView
import io.mockk.mockk
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test

class PresentersDependenciesTest : BaseDependenciesTest() {

    @Test
    fun `resolviendo dependencias para BusquedaConsultoraPresenter`() {
        val view = mockk<BusquedaConsultoraView>()
        get<BusquedaConsultoraPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para ConsultantsListPresenter`() {
        val view = mockk<ConsultantsListView>()
        get<ConsultantsListPresenter>(view).shouldNotBeNull()
    }

}
