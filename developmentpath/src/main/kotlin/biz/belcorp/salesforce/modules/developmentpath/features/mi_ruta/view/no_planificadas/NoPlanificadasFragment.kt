package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.no_planificadas

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.injectScoped
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.di.MI_RUTA_SCOPE
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.di.RDD_PRESENTER
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.NoPlanificadasViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.presenter.RddPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.EjecutorAccionesSobreVisita
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.SeccionMiRutaCards
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.planificadas.LocationLiveDataBusFragment
import biz.belcorp.salesforce.modules.developmentpath.utils.onLocationUpdated
import com.google.android.gms.maps.model.LatLng
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_mi_ruta_detalle.view.*

class NoPlanificadasFragment : LocationLiveDataBusFragment(), NoPlanificadasView {

    override fun getLayout() = R.layout.fragment_mi_ruta_detalle

    companion object {
        private const val SECCION_NO_PROGRAMADAS_TAG = "SeccionNoProgramadas"
        private const val SECCION_CANDIDATAS_PROACTIVAS_TAG = "SeccionPostulantes"
        private const val SECCION_CANDIDATAS_NO_PROACTIVAS_TAG = "SeccionCandidatasNoProactivas"
    }

    private val presenter by injectScoped<RddPresenter>(MI_RUTA_SCOPE, RDD_PRESENTER)

    private val firebaseAnalyticsPresenter: FirebaseAnalyticsPresenter by injectFragment()

    private val alClickearPersonaListener by lazy { EjecutorAccionesSobreVisita(this) }

    private lateinit var adapter: SectionedRecyclerViewAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configurarSectionedAdapter(view)
        configurarRecyclerView(view)
        presenter.noPlanificadasView = this
        presenter.cargarNoPlanificadas()
    }

    private fun configurarSectionedAdapter(view: View) {
        val seccionNoProgramadas = SeccionMiRutaCards(
            view.context,
            context?.getString(R.string.mi_ruta_header_seccion_no_programadas),
            R.drawable.ic_rdd_no_programadas,
            alClickearPersonaListener, firebaseAnalyticsPresenter
        )

        val seccionCandidatasProactivas = SeccionMiRutaCards(
            view.context,
            context?.getString(R.string.mi_ruta_header_seccion_candidatas_proactivas),
            R.drawable.ic_rdd_postulantes,
            alClickearPersonaListener, firebaseAnalyticsPresenter
        )

        val seccionCandidatasNoProactivas = SeccionMiRutaCards(
            view.context,
            context?.getString(R.string.mi_ruta_header_seccion_candidatas_no_proactivas),
            R.drawable.ic_rdd_postulantes,
            alClickearPersonaListener, firebaseAnalyticsPresenter
        )

        adapter = SectionedRecyclerViewAdapter()
        adapter.addSection(SECCION_CANDIDATAS_PROACTIVAS_TAG, seccionCandidatasProactivas)
        adapter.addSection(SECCION_CANDIDATAS_NO_PROACTIVAS_TAG, seccionCandidatasNoProactivas)
        adapter.addSection(SECCION_NO_PROGRAMADAS_TAG, seccionNoProgramadas)
    }

    private fun configurarRecyclerView(view: View) {
        view.rv_main?.layoutManager = LinearLayoutManager(context)
        view.rv_main?.adapter = adapter
    }

    override fun cargar(noPlanificadasViewModel: NoPlanificadasViewModel) {
        val seccionNoProgramadas =
            adapter.getSection(SECCION_NO_PROGRAMADAS_TAG) as SeccionMiRutaCards
        seccionNoProgramadas.actualizar(noPlanificadasViewModel.noPlanificadas)

        val seccionCandidatasProactivas =
            adapter.getSection(SECCION_CANDIDATAS_PROACTIVAS_TAG) as SeccionMiRutaCards
        seccionCandidatasProactivas.actualizar(noPlanificadasViewModel.postulantesProactivas)

        val seccionCandidatasNoProactivas =
            adapter.getSection(SECCION_CANDIDATAS_NO_PROACTIVAS_TAG) as SeccionMiRutaCards
        seccionCandidatasNoProactivas.actualizar(noPlanificadasViewModel.postulantesNoProactivas)
        adapter.notifyDataSetChanged()
    }

    override fun ocultarPostulantesProactivas() {
        val seccionPostulantes =
            adapter.getSection(SECCION_CANDIDATAS_PROACTIVAS_TAG) as SeccionMiRutaCards
        seccionPostulantes.isVisible = false
        adapter.notifySectionChangedToInvisible(
            SECCION_CANDIDATAS_PROACTIVAS_TAG,
            0
        )
    }

    override fun ocultarPostulantesNoProactivas() {
        val seccionPostulantes =
            adapter.getSection(SECCION_CANDIDATAS_NO_PROACTIVAS_TAG) as SeccionMiRutaCards
        seccionPostulantes.isVisible = false
        adapter.notifySectionChangedToInvisible(
            SECCION_CANDIDATAS_NO_PROACTIVAS_TAG,
            1
        )
    }

    override fun mostrarPostulantesProactivas() {
        val seccionPostulantes =
            adapter.getSection(SECCION_CANDIDATAS_PROACTIVAS_TAG) as SeccionMiRutaCards
        seccionPostulantes.isVisible = true
        adapter.notifySectionChangedToVisible(
            SECCION_CANDIDATAS_PROACTIVAS_TAG
        )
    }

    override fun mostrarPostulantesNoProactivas() {
        val seccionPostulantes =
            adapter.getSection(SECCION_CANDIDATAS_NO_PROACTIVAS_TAG) as SeccionMiRutaCards
        seccionPostulantes.isVisible = true
        adapter.notifySectionChangedToVisible(
            SECCION_CANDIDATAS_NO_PROACTIVAS_TAG
        )
    }

    override fun onConsultantLocationUpdated(consultantId: Int, location: LatLng) {
        adapter.onLocationUpdated(id = consultantId, location = location)
    }

}
