package biz.belcorp.salesforce.modules.developmentmaterial.features.dependencies

import biz.belcorp.salesforce.core.utils.get
import biz.belcorp.salesforce.modules.developmentmaterial.base.BaseDependenciesTest
import biz.belcorp.salesforce.modules.developmentmaterial.features.materials.MaterialesDesarrolloPresenter
import biz.belcorp.salesforce.modules.developmentmaterial.features.materials.MaterialesDesarrolloView
import io.mockk.mockk
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test


class PresentersDependenciesTest : BaseDependenciesTest() {

    @Test
    fun `resolviendo dependencias para MaterialesDesarrolloPresenter`() {
        val view = mockk<MaterialesDesarrolloView>()
        get<MaterialesDesarrolloPresenter>(view).shouldNotBeNull()
    }

}
