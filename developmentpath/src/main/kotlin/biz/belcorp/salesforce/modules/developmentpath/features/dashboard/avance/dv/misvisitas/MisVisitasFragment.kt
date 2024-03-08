package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.dv.misvisitas

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.features.flujo.Navigator
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.planificarapido.listarpersonas.PersonasEnPlanFragment
import kotlinx.android.synthetic.main.fragment_mis_visitas.*

private const val ARG_PLAN_ID = "planId"

class MisVisitasFragment : BaseFragment(),
    MisVisitasView,
    PersonasEnPlanFragment.PersonasEnPlanListener {

    override fun getLayout() = R.layout.fragment_mis_visitas

    val presenter by injectFragment<MisVisitasPresenter>()

    var navigator: Navigator? = null

    private var planId: Long = -1L

    private val recargarAvanceReceiver = RecargarAvanceReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { planId = it.getLong(ARG_PLAN_ID) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mis_visitas, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configurarBotonPlanificar()
        presenter.obtener()
        registrarReceiver()
    }

    override fun onDestroy() {
        desuscribirReceiver()
        super.onDestroy()
    }

    private fun configurarBotonPlanificar() {
        clPlanificar?.setOnClickListener { abrirPlanificar() }
    }

    private fun abrirPlanificar() {
        PersonasEnPlanFragment
            .newInstance(planId)
            .apply { personasEnPlanListener = this@MisVisitasFragment }
            .show(childFragmentManager, "PlanificarFragment")
    }

    override fun verMiRutaAlCerrarPersonasEnPlan(planId: Long) {
        navigator?.irAMiRuta(planId)
    }

    override fun pintar(visitadas: String, planificadas: String) {
        pbVisitas?.max = planificadas.toInt()
        pbVisitas?.progress = visitadas.toInt()
        tvPlanificadas?.text = "/$planificadas"
        tvVisitadas?.text = visitadas
    }

    private fun registrarReceiver() {
        val filter = IntentFilter(Constant.BROADCAST_CAMBIO_PLANIFICACION_RDD)
        activity?.registerReceiver(recargarAvanceReceiver, filter)
    }

    private fun desuscribirReceiver() {
        activity?.unregisterReceiver(recargarAvanceReceiver)
    }

    inner class RecargarAvanceReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            presenter.obtener()
        }
    }

    companion object {
        fun newInstance(planId: Long): MisVisitasFragment {
            return MisVisitasFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_PLAN_ID, planId)
                }
            }
        }
    }
}
