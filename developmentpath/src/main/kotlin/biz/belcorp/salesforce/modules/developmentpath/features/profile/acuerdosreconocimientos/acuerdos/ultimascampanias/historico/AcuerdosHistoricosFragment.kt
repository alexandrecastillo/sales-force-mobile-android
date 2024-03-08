package biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.ultimascampanias.historico

import android.animation.LayoutTransition
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import biz.belcorp.salesforce.core.base.BaseFragment
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.NonScrollableLayoutManager
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.withArguments
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.common.broadcast.SenderCambioAcuerdos
import biz.belcorp.salesforce.core.domain.entities.analytics.TagAnalytics
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.acuerdos.AcuerdosHistoricosUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.profile.acuerdosreconocimientos.acuerdos.ultimascampanias.historico.adapter.AcuerdosHistoricosPadreAdapter
import kotlinx.android.synthetic.main.fragment_acuerdos_historicos.*

class AcuerdosHistoricosFragment : BaseFragment(), AcuerdosHistoricosView {

    private var personaId = -1L
    private lateinit var rol: Rol
    private val senderCambioAcuerdos by injectFragment<SenderCambioAcuerdos>()
    private val presenter by injectFragment<AcuerdosHistoricosPresenter>()
    private val acuerdosNoCumplidosAdapter by lazy { instanciarAcuerdosAdapter() }
    private val acuerdosCumplidosAdapter by lazy { instanciarAcuerdosAdapter() }
    private val recycledViewPoolPadres by lazy { RecyclerView.RecycledViewPool() }
    private val recycledViewPoolDetalles by lazy { RecyclerView.RecycledViewPool() }
    private val firebaseAnalyticsPresenter by injectFragment<FirebaseAnalyticsPresenter>()

    private fun instanciarAcuerdosAdapter() =
        AcuerdosHistoricosPadreAdapter(recycledViewPoolDetalles)
            .apply {
                setClickEnCumplimientoListener { acuerdoId ->
                    presenter.invertirCumplimiento(acuerdoId)
                    presenter.eventoAnalitycs(acuerdoId)
                }
            }

    override fun getLayout(): Int = R.layout.fragment_acuerdos_historicos

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recuperarArgumentos()
    }

    private fun recuperarArgumentos() {
        arguments?.let {
            personaId = it.getLong(ARG_PERSONA_ID)
            rol = it.getSerializable(ARG_ROL) as Rol
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        animarTransiciones()
        configurarRecyclerAcuerdosNoCumplidos()
        configurarRecyclerAcuerdosCumplidos()
        cargarAcuerdos()
    }

    private fun animarTransiciones() {
        container.layoutTransition = LayoutTransition()
    }

    private fun cargarAcuerdos() {
        presenter.obtenerAcuerdosCampaniaActual(personaId, rol)
    }

    private fun configurarRecyclerAcuerdosNoCumplidos() {
        recyclerViewAcuerdosNoCumplidos.layoutManager =
            NonScrollableLayoutManager().withContext(context).linearVertical()
        recyclerViewAcuerdosNoCumplidos.adapter = acuerdosNoCumplidosAdapter
        recyclerViewAcuerdosNoCumplidos.setRecycledViewPool(recycledViewPoolPadres)
    }

    private fun configurarRecyclerAcuerdosCumplidos() {
        recyclerViewAcuerdosCumplidos.layoutManager =
            NonScrollableLayoutManager().withContext(context).linearVertical()
        recyclerViewAcuerdosCumplidos.adapter = acuerdosCumplidosAdapter
        recyclerViewAcuerdosNoCumplidos.setRecycledViewPool(recycledViewPoolPadres)
    }

    override fun pintarAcuerdosNoCumplidos(modelos: List<AcuerdosPorCampaniaModel>) {
        acuerdosNoCumplidosAdapter.actualizar(modelos)
    }

    override fun pintarAcuerdosCumplidos(modelos: List<AcuerdosPorCampaniaModel>) {
        acuerdosCumplidosAdapter.actualizar(modelos)
    }

    private inner class RegistroVisitaReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            presenter.obtenerAcuerdosCampaniaActual(personaId, rol)
        }
    }

    override fun mostrarSinAcuerdosCumplidos() {
        textViewSinAcuerdosCumplidos?.visibility = View.VISIBLE
    }

    override fun ocultarSinAcuerdosCumplidos() {
        textViewSinAcuerdosCumplidos?.visibility = View.GONE
    }

    override fun mostrarSinAcuerdosNoCumplidos() {
        textViewSinAcuerdosNoCumplidos?.visibility = View.VISIBLE
    }

    override fun ocultarSinAcuerdosNoCumplidos() {
        textViewSinAcuerdosNoCumplidos?.visibility = View.GONE
    }

    override fun mostrarSinAcuerdos() {
        textViewSinAcuerdos?.visibility = View.VISIBLE
    }

    override fun ocultarSinAcuerdos() {
        textViewSinAcuerdos?.visibility = View.GONE
    }

    override fun mostrarTituloAcuerdosNoCumplidos() {
        textViewTituloNoCumplidos?.visibility = View.VISIBLE
    }

    override fun mostrarTituloAcuerdosCumplidos() {
        textViewTituloCumplidos?.visibility = View.VISIBLE
    }

    override fun ocultarTituloAcuerdosCumplidos() {
        textViewTituloCumplidos?.visibility = View.GONE
    }

    override fun notificarCambioEnAcuerdos() {
        senderCambioAcuerdos.notificar()
    }

    override fun eventoaRegistarAnalytics(evento: AcuerdosHistoricosUseCase.EventoAnalyticsResponse?) {
        if (!isAttached()) return
        if (evento != null) {
            val campania = getString(R.string.acuerdos_historicos_item_titulo, evento.nombreCampania)
            if (rol == Rol.CONSULTORA) {
                if (evento.cumplido)
                    firebaseAnalyticsPresenter.enviarEventoTagConsultora(
                        TagAnalytics.EVENTO_ACUERDO_CUMPLIDO_CONSULTORA,
                        campania
                    )
                else
                    firebaseAnalyticsPresenter.enviarEventoTagConsultora(
                        TagAnalytics.EVENTO_REESTABLECER_ACUERDO_CONSULTORA,
                        campania
                    )
            }
        }
    }

    companion object {

        private const val ARG_PERSONA_ID = "param1"
        private const val ARG_ROL = "param2"

        fun newInstance(personaId: Long, rol: Rol) = AcuerdosHistoricosFragment()
            .withArguments(
                ARG_PERSONA_ID to personaId,
                ARG_ROL to rol
            )
    }
}
