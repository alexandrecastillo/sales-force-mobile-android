package biz.belcorp.salesforce.modules.auth.features.dependencies

import biz.belcorp.salesforce.core.utils.get
import biz.belcorp.salesforce.modules.auth.base.BaseDependenciesTest
import biz.belcorp.salesforce.modules.auth.features.business.LoginBusinessViewModel
import biz.belcorp.salesforce.modules.auth.features.support.LoginSupportViewModel
import org.amshove.kluent.shouldNotBeNull
import org.junit.*


class ViewModelDependenciesTest : BaseDependenciesTest() {

    @Test
    @Ignore("No funciona con Sonar")
    fun `resolviendo dependencias para LoginBusinessViewModel`() {
        get<LoginBusinessViewModel>().shouldNotBeNull()
    }

    @Test
    @Ignore("No funciona con Sonar")
    fun `resolviendo dependencias para LoginSupportViewModel`() {
        get<LoginSupportViewModel>().shouldNotBeNull()
    }

}
