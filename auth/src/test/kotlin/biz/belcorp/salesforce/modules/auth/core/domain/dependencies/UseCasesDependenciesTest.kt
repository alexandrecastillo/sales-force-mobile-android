package biz.belcorp.salesforce.modules.auth.core.domain.dependencies

import biz.belcorp.salesforce.modules.auth.base.BaseDependenciesTest
import biz.belcorp.salesforce.modules.auth.core.domain.usecases.LoginUseCase
import org.amshove.kluent.shouldNotBeNull
import org.junit.*
import org.koin.core.get


class UseCasesDependenciesTest : BaseDependenciesTest() {

    @Test
    @Ignore("No funciona con Sonar")
    fun `resolviendo dependencias para LoginSupportUseCase`() {
        get<LoginUseCase>().shouldNotBeNull()
    }

}
