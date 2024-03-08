package biz.belcorp.salesforce.modules.billing.core.domain.dependencies

import biz.belcorp.salesforce.modules.billing.base.BaseDependenciesTest
import biz.belcorp.salesforce.modules.billing.core.domain.repository.BillingDetailRepository
import biz.belcorp.salesforce.modules.billing.core.domain.repository.BillingRepository
import biz.belcorp.salesforce.modules.billing.core.domain.repository.RejectedOrdersRepository
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test
import org.koin.test.get

class RepositoriesDependenciesTest: BaseDependenciesTest() {

    @Test
    fun `solving test for BillingRepository`() {
        get<BillingRepository>().shouldNotBeNull()
    }

    @Test
    fun `solving test for BillingDetailRepository`() {
        get<BillingDetailRepository>().shouldNotBeNull()
    }

    @Test
    fun `solving test for RejectedOrdersRepository`() {
        get<RejectedOrdersRepository>().shouldNotBeNull()
    }
}
