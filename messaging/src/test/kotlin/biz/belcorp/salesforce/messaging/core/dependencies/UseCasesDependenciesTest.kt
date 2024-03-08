package biz.belcorp.salesforce.messaging.core.dependencies

import biz.belcorp.salesforce.core.utils.get
import biz.belcorp.salesforce.messaging.base.BaseDependenciesTest
import biz.belcorp.salesforce.messaging.core.domain.usecases.SaveNotificationUseCase
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test

class UseCasesDependenciesTest : BaseDependenciesTest() {

    @Test
    fun `solving test for SaveNotificationUseCase`() {
        get<SaveNotificationUseCase>().shouldNotBeNull()
    }

}
