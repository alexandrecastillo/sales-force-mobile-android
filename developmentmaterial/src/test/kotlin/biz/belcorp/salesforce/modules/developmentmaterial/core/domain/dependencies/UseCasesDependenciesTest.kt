package biz.belcorp.salesforce.modules.developmentmaterial.core.domain.dependencies

import biz.belcorp.salesforce.modules.developmentmaterial.base.BaseDependenciesTest
import biz.belcorp.salesforce.modules.developmentmaterial.core.domain.usecases.GetDocumentsUseCase
import biz.belcorp.salesforce.modules.developmentmaterial.core.domain.usecases.SyncDocumentsUseCase
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test
import org.koin.core.get


class UseCasesDependenciesTest : BaseDependenciesTest() {

    @Test
    fun `resolviendo dependencias para GetDocumentsUseCase`() {
        get<GetDocumentsUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para SyncDocumentsUseCase`() {
        get<SyncDocumentsUseCase>().shouldNotBeNull()
    }

}
