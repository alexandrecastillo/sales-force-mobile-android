package biz.belcorp.salesforce.modules.creditinquiry.features

import biz.belcorp.salesforce.core.utils.get
import biz.belcorp.salesforce.modules.creditinquiry.base.BaseDependenciesTest
import biz.belcorp.salesforce.modules.creditinquiry.features.presenters.ConsultaCrediticiaPresenter
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test

class PresentersDependenciesTest : BaseDependenciesTest() {

    @Test
    fun `resolviendo dependencias para ConsultaCrediticiaPresenter`() {
        get<ConsultaCrediticiaPresenter>().shouldNotBeNull()
    }


}
