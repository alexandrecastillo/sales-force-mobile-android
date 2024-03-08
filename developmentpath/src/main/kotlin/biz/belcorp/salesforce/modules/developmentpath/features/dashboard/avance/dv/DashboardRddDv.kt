package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.dv

import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.desarrollo.view.DesarrolloUaFragment
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.dv.avancecampania.view.AvanceCampaniaFragment
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.dv.avanceregiones.view.AvanceRegionesFragment
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.dv.avancevisitasequipos.AvanceVisitasEquiposFragment
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.dv.misvisitas.MisVisitasFragment
import biz.belcorp.salesforce.modules.developmentpath.features.flujo.Navigator

class DashboardRddDv : BaseFragment() {

    var navigator: Navigator? = null
    private var planId = -1L

    override fun getLayout() = R.layout.fragment_rdd_avance_pais

    private val desarrolloHabilidades: DesarrolloUaFragment by lazy {
        DesarrolloUaFragment.newInstance(planId)
    }

    private val avanceCampaniaFragment: AvanceCampaniaFragment by lazy {
        AvanceCampaniaFragment.newInstance(planId)
    }

    private val misVisitasFragment: MisVisitasFragment by lazy {
        MisVisitasFragment
            .newInstance(planId)
            .apply { navigator = this@DashboardRddDv.navigator }
    }

    private val avanceVisitasEquiposFragment: AvanceVisitasEquiposFragment by lazy {
        AvanceVisitasEquiposFragment.newInstance()
    }

    private val avanceRegionesFragment: AvanceRegionesFragment by lazy {
        AvanceRegionesFragment.newInstance()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgumentos()
    }

    private fun recuperarArgumentos() {
        arguments?.let { planId = it.getLong(PLAN_ID) }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        incrustarFragmentos()
    }

    private fun incrustarFragmentos() {
        if (context == null) return

        childFragmentManager.beginTransaction()
            .replace(R.id.frameLayoutDesarrolloHabilidades, desarrolloHabilidades)
            .replace(R.id.frameLayoutMisVisitas, misVisitasFragment)
            .replace(R.id.frameLayoutAvanceCampania, avanceCampaniaFragment)
            .replace(R.id.flAvanceVisitasMiEquipo, avanceVisitasEquiposFragment)
            .replace(R.id.frameLayoutAvanceRegiones, avanceRegionesFragment)
            .commit()
    }

    companion object {

        private const val PLAN_ID = "PLAN_ID"

        fun newInstance(planId: Long): DashboardRddDv {
            val bundle = Bundle()
            val fragment = DashboardRddDv()
            bundle.putLong(PLAN_ID, planId)
            fragment.arguments = bundle
            return fragment
        }
    }
}
