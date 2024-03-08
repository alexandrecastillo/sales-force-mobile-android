package biz.belcorp.salesforce.modules.orders.features.dependencies

import biz.belcorp.salesforce.core.utils.get
import biz.belcorp.salesforce.modules.orders.base.BaseDependenciesTest
import biz.belcorp.salesforce.modules.orders.features.results.PedidosWebPresenter
import biz.belcorp.salesforce.modules.orders.features.results.PedidosWebView
import biz.belcorp.salesforce.modules.orders.features.search.FiltrosPedidosWebPresenter
import biz.belcorp.salesforce.modules.orders.features.search.FiltrosPedidosWebView
import io.mockk.mockk
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test

class PresentersDependenciesTest : BaseDependenciesTest() {

    @Test
    fun `resolviendo dependencias para PedidosWebPresenter`() {
        val view = mockk<PedidosWebView>()
        get<PedidosWebPresenter>(view).shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para FiltrosPedidosWebPresenter`() {
        val view = mockk<FiltrosPedidosWebView>()
        get<FiltrosPedidosWebPresenter>(view).shouldNotBeNull()
    }

}
