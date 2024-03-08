package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.planificadas

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.injectScoped
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.di.MI_RUTA_SCOPE
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.di.RDD_PRESENTER
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.SenderIrAAgregarEvento
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.SenderIrAVerDetalleEvento
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.PlanificadasViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.presenter.RddPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.EjecutorAccionesSobreVisita
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.SeccionMiRutaCards
import biz.belcorp.salesforce.modules.developmentpath.utils.onLocationUpdated
import com.google.android.gms.maps.model.LatLng
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_mi_ruta_detalle.view.*
import java.util.*

class PlanificadasFragment :
    LocationLiveDataBusFragment(),
    PlanificadasView,
    SeccionEventos.ClickListener {

    override fun getLayout() = R.layout.fragment_mi_ruta_detalle

    companion object {
        private const val SECCION_HOY_TAG = "SeccionHoy"
        private const val SECCION_EVENTOS_TAG = "SeccionEventos"
    }

    private val alClickearPersonaListener by lazy { EjecutorAccionesSobreVisita(this) }

    private lateinit var adapter: SectionedRecyclerViewAdapter
    private val presenter by injectScoped<RddPresenter>(MI_RUTA_SCOPE, RDD_PRESENTER)
    private val senderIrAAgregarEvento: SenderIrAAgregarEvento by injectFragment()
    private val senderIrAVerDetalleEvento: SenderIrAVerDetalleEvento by injectFragment()

    private val firebaseAnalyticsPresenter: FirebaseAnalyticsPresenter by injectFragment()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        presenter.planificadasView = this
        configurarSectionedAdapter(view)
        configurarRecyclerView(view)
        presenter.cargarPlanificadas()
    }

    private fun configurarSectionedAdapter(view: View) {
        val seccionConsultoras = SeccionMiRutaCards(
            view.context,
            context?.getString(R.string.mi_ruta_header_seccion_hoy),
            R.drawable.ic_rdd_hoy,
            alClickearPersonaListener,
            firebaseAnalyticsPresenter
        )

        val seccionEventos = SeccionEventos(view.context)
        seccionEventos.itemClickListener = this

        adapter = SectionedRecyclerViewAdapter()
        adapter.addSection(SECCION_EVENTOS_TAG, seccionEventos)
        adapter.addSection(SECCION_HOY_TAG, seccionConsultoras)
    }

    private fun configurarRecyclerView(view: View) {
        view.rv_main?.layoutManager = LinearLayoutManager(context)
        view.rv_main?.adapter = adapter
    }

    override fun cargar(planificadasViewModel: PlanificadasViewModel) {
        val seccionEventos = adapter.getSection(SECCION_EVENTOS_TAG) as SeccionEventos
        val seccionHoy = adapter.getSection(SECCION_HOY_TAG) as SeccionMiRutaCards
        seccionEventos.configurarVisibilidadCrearEvento(planificadasViewModel.verAgregarEventos)
        seccionEventos.configurarFechaInicialCrearEvento(planificadasViewModel.diaSeleccionado)
        seccionEventos.actualizarEventos(planificadasViewModel.eventos)
        seccionHoy.actualizar(planificadasViewModel.planificadas)

        adapter.notifyDataSetChanged()
    }

    override fun onHeaderEventosClick(fecha: Date) {
        senderIrAAgregarEvento.ir(fecha)
    }

    override fun onItemEventosClick(eventoXUaId: Long) {
        senderIrAVerDetalleEvento.ir(eventoXUaId)
    }

    override fun onConsultantLocationUpdated(consultantId: Int, location: LatLng) {
        adapter.onLocationUpdated(id = consultantId, location = location)
    }

}
