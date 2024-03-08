package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.planificarapido.planificar

import android.os.Bundle
import android.view.View
import android.widget.Button
import biz.belcorp.salesforce.core.base.BaseDialogFragment
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.common.broadcast.SenderPlanificacionVisitaExitosa
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.ruta.data.SenderCambioRDD
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.planificar.PlanificarSuccessFragment
import kotlinx.android.synthetic.main.fragment_mi_ruta_planificar_rapido.*
import biz.belcorp.salesforce.core.utils.toast
import java.util.*

class PlanificarRapidoFragment : BaseDialogFragment(),
                                 PlanificarRapidoView,
                                 PlanificarSuccessFragment.Listener {

    override fun getLayout(): Int {
        return R.layout.fragment_mi_ruta_planificar_rapido
    }

    private var visitaId = -1L

    private val presenter: PlanificarRapidoPresenter by injectFragment()

    private val senderCambioRDD: SenderCambioRDD by injectFragment()

    private val senderExitoPlanificacion: SenderPlanificacionVisitaExitosa by injectFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        visitaId = arguments!!.getLong(ID_VISITA)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        configurarBotones()
        presenter.view = this
        presenter.cargarDatosDeVisita(visitaId)
    }

    private fun configurarBotones() {
        botonPlanificar?.configurarBotonPlanificar()
        botonCerrar?.setOnClickListener { dismiss() }
        layoutCancelar?.setOnClickListener { dismiss() }
    }

    private fun Button.configurarBotonPlanificar() {
        this.setOnClickListener {
            if (!validarFecha()) return@setOnClickListener
            presenter.planificar(visitaId, fechaSeleccionada())
        }
    }

    private fun validarFecha(): Boolean {
        if (fechaSeleccionada().before(Date())) {
            toast("La hora ingresada debe ser posterior")
            return false
        }

        return true
    }

    private fun fechaSeleccionada(): Date {
        return combinarFechaYHora(selectorFecha.fechaSeleccionadaCalendar,
                                  selectorHora.horaSeleccionadaCalendar).time
    }

    private fun combinarFechaYHora(fecha: Calendar, hora: Calendar): Calendar {
        return fecha.apply {
            set(Calendar.HOUR_OF_DAY, hora.get(Calendar.HOUR_OF_DAY))
            set(Calendar.MINUTE, hora.get(Calendar.MINUTE))
        }
    }

    override fun onStart() {
        super.onStart()
        fitFullScreen()
    }

    override fun pintarNombrePersona(nombre: String) {
        tituloTextView?.text = getString(R.string.rdd_planificarapido_titulo, nombre)
    }

    override fun cerrar() {
        dismiss()
    }

    override fun mostrarError(mensaje: String) {
        toast(mensaje)
    }

    override fun notificarExitoPlanificacion() {
        senderExitoPlanificacion.notificarExitoPlanificar()
    }

    override fun notificarCambioPlan() {
        senderCambioRDD.notificarCambioEnPlanificacion()
    }

    override fun alCerrarDialogoDeExito() {
        dismiss()
    }

    companion object {

        private const val ID_VISITA = "ID_PERSONA"

        fun newInstance(visitaId: Long): PlanificarRapidoFragment {
            val args = Bundle()
            val fragment = PlanificarRapidoFragment()
            args.putLong(ID_VISITA, visitaId)
            fragment.arguments = args

            return fragment
        }
    }
}
