package biz.belcorp.salesforce.modules.billing.features.billing.view.detail.utils

import android.content.Context
import androidx.annotation.StringRes
import biz.belcorp.salesforce.components.utils.TabItem
import biz.belcorp.salesforce.components.utils.createTabs
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.FilterKey
import biz.belcorp.salesforce.core.constants.SourceType
import biz.belcorp.salesforce.modules.billing.R
import com.google.android.material.tabs.TabLayout

object TabsCreator {

    private var tabLayout: TabLayout? = null
    private var context: Context? = null

    fun with(tabLayout: TabLayout): TabsCreator {
        this.tabLayout = tabLayout
        this.context = tabLayout.context
        return this
    }

    fun create(sourceType: Int) = when (sourceType) {
        SourceType.BILLING_ADVANCEMENT -> createBillingAdavancement()
        else -> Unit
    }

    private fun createBillingAdavancement() {
        tabLayout?.apply {
            createTabs(
                listOf(
                    TabItem(getString(R.string.title_pending_to_billing), FilterKey.KEY_NO_BILLED_ORDERS),
                    TabItem(getString(R.string.title_in_billing), FilterKey.KEY_BILLED_ORDERS)
                )
            )
        }
    }

    private fun getString(@StringRes resId: Int) = context?.getString(resId) ?: EMPTY_STRING
}
