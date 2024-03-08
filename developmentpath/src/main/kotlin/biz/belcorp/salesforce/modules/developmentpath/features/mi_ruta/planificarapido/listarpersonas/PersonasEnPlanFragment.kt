package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.planificarapido.listarpersonas

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.setOnOneClickListener
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.features.dashboard.barraalerta.BarraAlertaFragment
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.planificarapido.planificar.PlanificarRapidoFragment
import kotlinx.android.synthetic.main.fragment_planifica_rapido.*

class PersonasEnPlanFragment : BaseDialogFragment(), PersonasEnPlanView {

    private val presenter: PersonasEnPlanPresenter by injectFragment()

    var personasEnPlanListener: PersonasEnPlanListener? = null

    private var planId: Long = -1L
    private val recalculoReceiver = RecalculoReceiver()

    private val personasEnPlanAdapter: PersonasEnPlanAdapter by lazy {
        val adapter = PersonasEnPlanAdapter(requireContext())
        adapter.setItemClickListener { visitaId -> mostrarPlanificarRapido(visitaId) }

        return@lazy adapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { planId = it.getLong(ARG_PLAN_ID) }
    }

    override fun getLayout() = R.layout.fragment_planifica_rapido

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registrarCambioPlanificacionBroadcast()
        incrustarFragmentAviso()
        configurarBotonRetroceder()
        configurarVerMiRuta()
        configurarRecycler()
        presenter.view = this
        presenter.recuperarPersonas(planId)
    }

    private fun registrarCambioPlanificacionBroadcast() {
        val filter = IntentFilter(Constant.BROADCAST_CAMBIO_PLANIFICACION_RDD)
        activity?.registerReceiver(recalculoReceiver, filter)
    }

    private fun incrustarFragmentAviso() {
        val fragment = BarraAlertaFragment()
                .conVistaRaiz(frameLayoutAviso)
                .alertarAlPlanificarVisitas()

        childFragmentManager
                .beginTransaction()
                .add(R.id.frameLayoutAviso, fragment)
                .commit()
    }

    private fun configurarBotonRetroceder() {
        btnRetroceder?.setOnClickListener { dismiss() }
    }
    private fun configurarVerMiRuta() {
       botonIrAMiRuta?.setOnOneClickListener {
           personasEnPlanListener?.verMiRutaAlCerrarPersonasEnPlan(planId)
           dismiss()
       }
    }

    private fun configurarRecycler() {
        recyclerVisitas.layoutManager = LinearLayoutManager(context)
        recyclerVisitas.adapter = personasEnPlanAdapter
    }

    override fun onStart() {
        super.onStart()
        fitFullScreen()
    }

    override fun onDestroyView() {
        activity?.unregisterReceiver(recalculoReceiver)
        super.onDestroyView()
    }

    override fun pintarPersonas(modelos: List<PersonaEnPlanModel>) {
        personasEnPlanAdapter.actualizar(modelos)
    }

    private fun mostrarPlanificarRapido(visitaId: Long) {
        PlanificarRapidoFragment
                .newInstance(visitaId)
                .show(childFragmentManager, "PlanificarRapido")
    }

    interface PersonasEnPlanListener {
        fun verMiRutaAlCerrarPersonasEnPlan(planId: Long)
    }

    inner class RecalculoReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            presenter.recuperarPersonas(planId)
        }
    }

    companion object {
        private const val ARG_PLAN_ID = "planId"

        fun newInstance(planId: Long): PersonasEnPlanFragment {
            return PersonasEnPlanFragment().apply {
                arguments = Bundle().apply {
                    putLong(ARG_PLAN_ID, planId)
                }
            }
        }
    }
}
