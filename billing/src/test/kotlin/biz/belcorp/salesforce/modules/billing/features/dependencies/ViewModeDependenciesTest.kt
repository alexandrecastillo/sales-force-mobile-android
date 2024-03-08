package biz.belcorp.salesforce.modules.billing.features.dependencies

import biz.belcorp.salesforce.core.utils.get
import biz.belcorp.salesforce.modules.billing.base.BaseDependenciesTest
import biz.belcorp.salesforce.modules.billing.features.billing.view.BillingViewModel
import biz.belcorp.salesforce.modules.billing.features.billing.view.detail.BillingMultiProfileDetailViewModel
import biz.belcorp.salesforce.modules.billing.features.billing.view.header.multiprofile.BillingMultiProfileHeaderViewModel
import biz.belcorp.salesforce.modules.billing.features.billing.view.header.se.BillingHeaderViewModel
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test

class ViewModeDependenciesTest : BaseDependenciesTest() {

    @Test
    fun `solving dependencies for BillingViewModel`() {
        get<BillingViewModel>().shouldNotBeNull()
    }

    @Test
    fun `solving dependencies for BillingHeaderViewModel`() {
        get<BillingHeaderViewModel>().shouldNotBeNull()
    }

    @Test
    fun `solving dependencies for BillingMultiProfileHeaderViewModel`() {
        get<BillingMultiProfileHeaderViewModel>().shouldNotBeNull()
    }

    @Test
    fun `solving dependencies for BillingMultiProfileDetailViewModel`() {
        get<BillingMultiProfileDetailViewModel>().shouldNotBeNull()
    }
}
