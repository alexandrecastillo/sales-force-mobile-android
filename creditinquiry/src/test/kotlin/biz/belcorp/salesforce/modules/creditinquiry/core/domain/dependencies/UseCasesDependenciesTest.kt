package biz.belcorp.salesforce.modules.creditinquiry.core.domain.dependencies

import biz.belcorp.salesforce.modules.creditinquiry.base.BaseDependenciesTest
import biz.belcorp.salesforce.modules.creditinquiry.core.domain.usecases.ConsultaCrediticiaUseCase
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test
import org.koin.core.get

class UseCasesDependenciesTest : BaseDependenciesTest() {

    @Test
    fun `resolviendo dependencias para ConsultaCrediticiaUseCase`() {
        get<ConsultaCrediticiaUseCase>().shouldNotBeNull()
    }
}
