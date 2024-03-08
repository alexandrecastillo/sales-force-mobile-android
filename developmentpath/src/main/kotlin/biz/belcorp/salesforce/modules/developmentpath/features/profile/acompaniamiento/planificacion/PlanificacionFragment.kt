package biz.belcorp.salesforce.modules.developmentpath.features.profile.acompaniamiento.planificacion

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.Button
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.ruta.data.SenderCambioRDD
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant.BROADCAST_CAMBIO_PLANIFICACION_RDD
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.adicionar.AdicionarVisitaFragment
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.planificar.PlanificarFragment
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.registrar.RegistrarVisitaFragment
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.replanificar.ReprogramarFragment
import kotlinx.android.synthetic.main.fragment_acciones_visita.*
import java.util.*

class PlanificacionFragment : BaseFragment(), PlanificacionView {

    var btnRegistrarVisita: Button? = null

    private var personaId: Long = -1
    private lateinit var rol: Rol
    private var notificacionRddReciever = NotificacionRddReceiver()
    private val presenter by injectFragment<PlanificacionPresenter>()
    private val senderCambioRDD by injectFragment<SenderCambioRDD>()
    private val firebaseAnalyticsPresenter by injectFragment<FirebaseAnalyticsPresenter>()

    override fun getLayout(): Int = R.layout.fragment_acciones_visita

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgs()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configurarRecyclerHistorialVisitas()
        registrarBroadcastReceiver()
        presenter.calcularEstado(personaId, rol)
    }

    private fun recuperarArgs() {
        arguments?.let {
            personaId = it.getLong(ARG_PERSONA_ID)
            rol = it.getSerializable(ARG_ROL) as Rol
        }
    }

    private fun registrarBroadcastReceiver() {
        val filter = IntentFilter(BROADCAST_CAMBIO_PLANIFICACION_RDD)
        activity?.registerReceiver(notificacionRddReciever, filter)
    }

    private fun configurarRecyclerHistorialVisitas() {
        rvVisitas.layoutManager = NonScrollableLayoutManager()
            .withContext(context)
            .linearVertical()
        rvVisitas.adapter = HistorialVisitasAdapter()
    }

    override fun onDestroyView() {
        activity?.unregisterReceiver(notificacionRddReciever)
        super.onDestroyView()
    }

    override fun mostrarCargando() {
        tvCargando?.visible()
    }

    override fun ocultarCargando() {
        tvCargando?.gone()
    }

    override fun mostrarNoPlanificada() {
        cardNoplanificada?.visible()
    }

    override fun mostrarReplanificar() {
        cardReplanificar?.visible()
    }

    override fun ocultarReplanificar() {
        cardReplanificar?.gone()
    }

    override fun ocultarNoPlanificada() {
        cardNoplanificada?.gone()
    }

    override fun hablitarBotonRegistroOtrosRoles() {
        btnRegistrarVisita?.isEnabled = true
    }

    override fun deshabilitarBotonRegistroOtrosRoles() {
        btnRegistrarVisita?.isEnabled = false
    }

    override fun habilitarBotonRegistroConsultora() {
        btnRegistrarVisita?.isEnabled = true
    }

    override fun deshabilitarBotonRegistroConsultora() {
        btnRegistrarVisita?.isEnabled = false
    }

    override fun cargarFecha(fecha: String?) {
        txtDataPlan?.text = fecha
    }

    override fun cargarHora(hora: String?) {
        txtHoursPlan?.text = hora
    }

    override fun configurarBotonAdicionarVisita(personaId: Long, rol: Rol) {
        btnPlanificar?.setOnClickListener {
            firebaseAnalyticsPresenter.enviarEventoPorRol(
                TagAnalytics.EVENTO_PLANIFICAR_ACOMPANIAMIENTO_VISITA,
                rol
            )
            AdicionarVisitaFragment.newInstance(personaId, rol)
                .show(childFragmentManager, "Adicionar")
        }
    }

    override fun configurarBotonPlanificar(visitaId: Long) {
        btnPlanificar?.setOnClickListener {
            firebaseAnalyticsPresenter.enviarEventoPorRol(
                TagAnalytics.EVENTO_PLANIFICAR_ACOMPANIAMIENTO_VISITA,
                rol
            )
            PlanificarFragment.newInstance(visitaId)
                .show(childFragmentManager, "Planificar")
        }
    }

    override fun configurarBotonRegistroValido(visitaId: Long) {
        btnRegistrarVisita?.setOnClickListener {
            firebaseAnalyticsPresenter.enviarEventoPorRol(TagAnalytics.EVENTO_REGISTRAR_VISITA, rol)
            lanzarFragmentRegistro(visitaId)
        }
    }

    private fun lanzarFragmentRegistro(visitaId: Long) {
        if (!isResumed) return
        RegistrarVisitaFragment.newInstance(visitaId = visitaId)
            .show(childFragmentManager, "Registro")
    }

    override fun configurarBotonRegistroInvalido() {
        btnRegistrarVisita?.setOnClickListener {
            toast(R.string.registro_visitas_invalido)
        }
    }

    override fun configurarBotonReprogramacion(visitaId: Long, fechaAnterior: Date) {
        btnReplanificar?.setOnClickListener {
            firebaseAnalyticsPresenter.enviarEventoPorRol(
                TagAnalytics.EVENTO_RE_PLANIFICAR_ACOMPANIAMIENTO_VISITA,
                rol
            )
            ReprogramarFragment.newInstance(visitaId, fechaAnterior)
                .show(childFragmentManager, "Reprogramacion")
        }
    }

    override fun mostrarHistorialVisitas() {
        cardVisitasRegistradas?.visible()
    }

    override fun ocultarHistorialVisitas() {
        cardVisitasRegistradas?.gone()
    }

    override fun cargarNombreCampania(campania: String) {
        tvTitulo?.text = getString(R.string.acompaniamiento_tab_titulo_historial_visitas, campania)
    }

    override fun cargarVisitas(visitas: List<VisitaModel>) {
        (rvVisitas?.adapter as? HistorialVisitasAdapter)?.actualizar(visitas)
    }

    override fun notificarAOtrosModulos() {
        senderCambioRDD.notificarCambioEnPlanificacion()
    }

    private inner class NotificacionRddReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            presenter.calcularEstado(personaId, rol)
        }
    }

    companion object {

        private const val ARG_PERSONA_ID = "id"
        private const val ARG_ROL = "rol"

        fun newInstance(personaId: Long, rol: Rol) = PlanificacionFragment()
            .withArguments(
                ARG_PERSONA_ID to personaId,
                ARG_ROL to rol
            )
    }
}
