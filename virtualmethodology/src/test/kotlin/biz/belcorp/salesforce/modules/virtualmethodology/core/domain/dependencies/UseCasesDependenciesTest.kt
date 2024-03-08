package biz.belcorp.salesforce.modules.virtualmethodology.core.domain.dependencies

import biz.belcorp.salesforce.modules.virtualmethodology.base.BaseDependenciesTest
import biz.belcorp.salesforce.modules.virtualmethodology.core.domain.usecases.GetGroupSegUseCase
import biz.belcorp.salesforce.modules.virtualmethodology.core.domain.usecases.SyncGroupSegUseCase
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test
import org.koin.core.get


class UseCasesDependenciesTest : BaseDependenciesTest() {

    @Test
    fun `resolviendo dependencias para GetGroupSegUseCase`() {
        get<GetGroupSegUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para SyncGroupSegUseCase`() {
        get<SyncGroupSegUseCase>().shouldNotBeNull()
    }

}
