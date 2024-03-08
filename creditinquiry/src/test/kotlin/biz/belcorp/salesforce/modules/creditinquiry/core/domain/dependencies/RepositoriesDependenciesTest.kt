package biz.belcorp.salesforce.modules.creditinquiry.core.domain.dependencies

import biz.belcorp.salesforce.modules.creditinquiry.base.BaseDependenciesTest
import biz.belcorp.salesforce.modules.creditinquiry.core.domain.repository.ConsultaCrediticiaRepository
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test
import org.koin.core.get

class RepositoriesDependenciesTest : BaseDependenciesTest() {

    @Test
    fun `resolviendo dependencias para ConsultaCrediticiaRepository`() {
        get<ConsultaCrediticiaRepository>().shouldNotBeNull()
    }
}
