package biz.belcorp.salesforce.modules.consultants.includes

import androidx.fragment.app.Fragment
import biz.belcorp.salesforce.core.include.Include
import biz.belcorp.salesforce.core.include.ModuleIncludes
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.view.SearchConsultantFragment
import biz.belcorp.salesforce.modules.consultants.features.list.ConsultantsListFragment
import biz.belcorp.salesforce.modules.consultants.features.quantity.ConsultantsQuantityFragment

class ConsultantsModuleIncludes : ModuleIncludes {

    override val includesId: List<Int>
        get() = listOf(
            Include.CONSULTANTS_QUANTITY,
            Include.BILLING_ADVANCEMENT,
            Include.LEGACY_CONSULTANT_LIST
        )

    override fun getInclude(includeId: Int): Fragment? {
        return when (includeId) {
            Include.CONSULTANTS_QUANTITY -> ConsultantsQuantityFragment()
            Include.BILLING_ADVANCEMENT -> SearchConsultantFragment()
            Include.LEGACY_CONSULTANT_LIST -> ConsultantsListFragment()
            else -> null
        }
    }

}
