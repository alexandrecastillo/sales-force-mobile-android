package biz.belcorp.salesforce.modules.developmentpath.includes

import androidx.fragment.app.Fragment
import biz.belcorp.salesforce.core.include.Include
import biz.belcorp.salesforce.core.include.ModuleIncludes
import biz.belcorp.salesforce.modules.developmentpath.features.profile.PerfilFragment


class DevelopmentPathModuleIncludes : ModuleIncludes {

    override val includesId: List<Int>
        get() = listOf(
            Include.PROFILE
        )

    override fun getInclude(includeId: Int): Fragment? {
        return when (includeId) {
            Include.PROFILE -> PerfilFragment()
            else -> null
        }
    }

}
