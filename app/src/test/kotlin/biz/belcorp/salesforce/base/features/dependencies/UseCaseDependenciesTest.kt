package biz.belcorp.salesforce.base.features.dependencies

import biz.belcorp.salesforce.base.base.BaseDependenciesTest
import biz.belcorp.salesforce.base.core.domain.usecases.sync.SyncUseCase
import biz.belcorp.salesforce.core.utils.get
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test

class UseCaseDependenciesTest : BaseDependenciesTest() {

    @Test
    fun `resolve dependencies for SyncUseCase`() {
        get<SyncUseCase>().shouldNotBeNull()
    }
}
