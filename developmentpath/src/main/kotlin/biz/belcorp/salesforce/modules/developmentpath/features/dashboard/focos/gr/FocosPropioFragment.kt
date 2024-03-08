package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.focos.gr

import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.modules.developmentpath.features.focos.AsignarFragment

import kotlinx.android.synthetic.main.fragment_focos_propio.*

class FocosPropioFragment : BaseFragment() {
    override fun getLayout() = R.layout.fragment_focos_propio

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        txtAsignar?.setOnClickListener {
            abrirAsignarFocosPropio()
        }
    }

    private fun abrirAsignarFocosPropio() {
        AsignarFragment
            .newInstance(
                AsignarFragment.Request.EditarPropio(),
                TagAnalytics.EVENTO_ASIGNAR_PERSONAS
            )
            .show(childFragmentManager, "AsignarFocosFragment")
    }

}
