package biz.belcorp.salesforce.modules.billing.core.domain.dependencies

import biz.belcorp.salesforce.modules.billing.base.BaseDependenciesTest
import biz.belcorp.salesforce.modules.billing.core.domain.usecases.BillingMultiProfileDetailUseCase
import biz.belcorp.salesforce.modules.billing.core.domain.usecases.GetBillingAdvancementUseCase
import biz.belcorp.salesforce.modules.billing.core.domain.usecases.rejectedorders.RejectedOrdersSyncUseCase
import biz.belcorp.salesforce.modules.billing.core.domain.usecases.rejectedorders.RejectedOrdersUseCase
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test
import org.koin.test.get

class UseCasesDependenciesTest : BaseDependenciesTest() {

    @Test
    fun `solving test for RejectedOrdersSyncUseCase`() {
        get<RejectedOrdersSyncUseCase>().shouldNotBeNull()
    }

    @Test
    fun `solving test for RejectedOrdersUseCase`() {
        get<RejectedOrdersUseCase>().shouldNotBeNull()
    }

    @Test
    fun `solving test for BillingMultiProfileDetailUseCase`() {
        get<BillingMultiProfileDetailUseCase>().shouldNotBeNull()
    }

    @Test
    fun `solving test for GetBillingAdvancementUseCase`() {
        get<GetBillingAdvancementUseCase>().shouldNotBeNull()
    }
}
