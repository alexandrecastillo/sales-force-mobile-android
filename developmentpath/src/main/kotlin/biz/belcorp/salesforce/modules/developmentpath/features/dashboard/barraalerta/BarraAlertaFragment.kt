package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.barraalerta

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.view.View
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import kotlinx.android.synthetic.main.fragment_rdd_barra_alerta.*

class BarraAlertaFragment : BaseFragment() {

    override fun getLayout() = R.layout.fragment_rdd_barra_alerta

    private var vistaRaiz: View? = null
    private val recargarFocosReceiver = RecargarFocosReceiver()
    private val recargarHabilidadesReceiver = RecargarHabilidadesReceiver()
    private val planificarVisitaReceiver = PlanificarVisitaReceiver()

    private var alertarAlEditarFocos = false
    private var alertarAlEditarHabilidades = false
    private var alertarAlPlanificarVisitas = false

    fun conVistaRaiz(view: View): BarraAlertaFragment {
        vistaRaiz = view
        vistaRaiz?.visibility = View.GONE

        return this
    }

    fun alertarAlEditarFocos(): BarraAlertaFragment {
        alertarAlEditarFocos = true

        return this
    }

    fun alertarAlEditarHabilidades(): BarraAlertaFragment {
        alertarAlEditarHabilidades = true

        return this
    }

    fun alertarAlPlanificarVisitas(): BarraAlertaFragment {
        alertarAlPlanificarVisitas = true

        return this
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (alertarAlEditarFocos) {
            registrarRecargarFocosReceiver()
            registrarRecargarMisFocosReceiver()
        }

        if (alertarAlEditarHabilidades) {
            registrarRecargarHabilidadesEquipoReceiver()
            registrarRecargarHabilidadesPropiasReceiver()
        }

        if (alertarAlPlanificarVisitas) {
            registrarVisitaPlanificadaReceiver()
        }
    }

    private fun registrarRecargarFocosReceiver() {
        val filter = IntentFilter(Constant.BROADCAST_RECARGAR_FOCOS)
        activity?.registerReceiver(recargarFocosReceiver, filter)
    }

    private fun registrarRecargarMisFocosReceiver() {
        val filter = IntentFilter(Constant.BROADCAST_RECARGAR_MIS_FOCOS)
        activity?.registerReceiver(recargarFocosReceiver, filter)
    }

    private fun registrarRecargarHabilidadesEquipoReceiver() {
        val filter = IntentFilter(Constant.BROADCAST_RECARGAR_HABILIDADES_EQUIPO)
        activity?.registerReceiver(recargarHabilidadesReceiver, filter)
    }

    private fun registrarRecargarHabilidadesPropiasReceiver() {
        val filter = IntentFilter(Constant.BROADCAST_RECARGAR_HABILIDADES_PROPIAS)
        activity?.registerReceiver(recargarHabilidadesReceiver, filter)
    }

    private fun registrarVisitaPlanificadaReceiver() {
        val filter = IntentFilter(Constant.BROADCAST_PLANIFICAR_VISITA)
        activity?.registerReceiver(planificarVisitaReceiver, filter)
    }

    override fun onDestroyView() {
        if (alertarAlEditarFocos) activity?.unregisterReceiver(recargarFocosReceiver)
        if (alertarAlEditarHabilidades) activity?.unregisterReceiver(recargarHabilidadesReceiver)
        if (alertarAlPlanificarVisitas) activity?.unregisterReceiver(planificarVisitaReceiver)
        super.onDestroyView()
    }

    private fun mostrarAvisoFocos() {
        textViewTitulo?.text = getString(R.string.rdd_dashboard_alerta_focos_asignados)
        mostrarAvisoTemporalmente()
    }

    private fun mostrarAvisoHabilidades() {
        textViewTitulo?.text = getString(R.string.rdd_dashboard_alerta_habilidades_asignadas)
        mostrarAvisoTemporalmente()
    }

    private fun mostrarAvisoVisitaPlanificada() {
        textViewTitulo?.text = getString(R.string.rdd_dashboard_alerta_visita_planificada)
        mostrarAvisoTemporalmente()
    }

    private fun mostrarAvisoTemporalmente() {
        vistaRaiz?.visibility = View.VISIBLE
        Handler().postDelayed({ vistaRaiz?.visibility = View.GONE }, 3000)
    }

    inner class RecargarFocosReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            mostrarAvisoFocos()
        }
    }

    inner class RecargarHabilidadesReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            mostrarAvisoHabilidades()
        }
    }

    inner class PlanificarVisitaReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            mostrarAvisoVisitaPlanificada()
        }
    }
}
