package biz.belcorp.salesforce.messaging.core.dependencies

import biz.belcorp.salesforce.core.utils.get
import biz.belcorp.salesforce.messaging.base.BaseDependenciesTest
import biz.belcorp.salesforce.messaging.core.domain.repository.NotificationsRepository
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test

class RepositoriesDependenciesTest : BaseDependenciesTest() {

    @Test
    fun `solving test for NotificationsRepository`() {
        get<NotificationsRepository>().shouldNotBeNull()
    }

}
