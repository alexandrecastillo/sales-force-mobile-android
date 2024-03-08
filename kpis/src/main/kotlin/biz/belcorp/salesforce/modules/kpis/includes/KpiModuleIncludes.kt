package biz.belcorp.salesforce.modules.kpis.includes

import androidx.fragment.app.Fragment
import biz.belcorp.salesforce.core.include.Include
import biz.belcorp.salesforce.core.include.ModuleIncludes
import biz.belcorp.salesforce.modules.kpis.features.kpis.KpisFragment


class KpiModuleIncludes : ModuleIncludes {

    override val includesId: List<Int>
        get() = listOf(Include.DASHBOARD_KPIS)

    override fun getInclude(includeId: Int): Fragment? {
        return when (includeId) {
            Include.DASHBOARD_KPIS -> KpisFragment()
            else -> null
        }
    }

}
