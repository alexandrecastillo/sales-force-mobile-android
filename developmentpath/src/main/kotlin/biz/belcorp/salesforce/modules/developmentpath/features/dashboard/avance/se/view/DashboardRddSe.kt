package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.se.view

import android.os.Bundle
import android.view.View
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.desarrollo.view.DesarrolloUaFragment
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.visitas.view.AvanceVisitasFragment
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.reconocimientos_superior.view.ReconocimientosASuperiorFragment
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.resultadosvisita.ResultadoVisitasFragment
import biz.belcorp.salesforce.modules.developmentpath.features.flujo.Navigator

class DashboardRddSe : BaseFragment() {

    override fun getLayout() = R.layout.fragment_rdd_mi_seccion

    var navigator: Navigator? = null
    private var planId = -1L

    companion object {

        private const val PLAN_ID = "PLAN_ID"

        fun newInstance(planId: Long): DashboardRddSe {
            val fragment = DashboardRddSe()
            val args = Bundle()
            args.putLong(PLAN_ID, planId)
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgs()
    }

    private fun recuperarArgs() {
        arguments?.let { planId = it.getLong(PLAN_ID) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        agregarHijosDeFragment()
    }

    private fun agregarHijosDeFragment() {
        val desarrolloUaComportamientosFragment = DesarrolloUaFragment.newInstance(planId)
        val avanceVisitasFragment = AvanceVisitasFragment.newInstance(planId, Rol.SOCIA_EMPRESARIA)
        val reconocimientosASuperiorFragment = ReconocimientosASuperiorFragment.newInstance()
        val resultadoVisitasFragment = ResultadoVisitasFragment()
        resultadoVisitasFragment.navigator = navigator

        childFragmentManager
            .beginTransaction()
            .replace(R.id.framelayoutIrADesarrolloSeccion, desarrolloUaComportamientosFragment)
            .replace(R.id.framelayoutAvanceVisitas, avanceVisitasFragment)
            .replace(R.id.framelayoutReconocimientos, reconocimientosASuperiorFragment)
            .replace(R.id.framelayoutResultadoVisitas, resultadoVisitasFragment)
            .commit()
    }
}
