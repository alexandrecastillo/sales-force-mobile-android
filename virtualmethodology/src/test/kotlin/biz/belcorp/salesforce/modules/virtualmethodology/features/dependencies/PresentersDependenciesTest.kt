package biz.belcorp.salesforce.modules.virtualmethodology.features.dependencies

import biz.belcorp.salesforce.core.utils.get
import biz.belcorp.salesforce.modules.virtualmethodology.base.BaseDependenciesTest
import biz.belcorp.salesforce.modules.virtualmethodology.features.methodology.MethodologyView
import biz.belcorp.salesforce.modules.virtualmethodology.features.methodology.VirtualMethodologyPresenter
import io.mockk.mockk
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test


class PresentersDependenciesTest : BaseDependenciesTest() {

    @Test
    fun `resolviendo dependencias para VirtualMethodologyPresenter`() {
        val view = mockk<MethodologyView>()
        get<VirtualMethodologyPresenter>(view).shouldNotBeNull()
    }

}
