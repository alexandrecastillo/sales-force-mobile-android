package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.busqueda

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import biz.belcorp.salesforce.core.utils.gone
import biz.belcorp.salesforce.core.utils.injectFragment
import biz.belcorp.salesforce.core.utils.injectScoped
import biz.belcorp.salesforce.core.utils.visible
import biz.belcorp.salesforce.modules.developmentpath.R
import biz.belcorp.salesforce.modules.developmentpath.features.analytics.FirebaseAnalyticsPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.di.MI_RUTA_SCOPE
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.di.RDD_PRESENTER
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.BusquedaViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.presenter.RddPresenter
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.EjecutorAccionesSobreVisita
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.SeccionMiRutaCards
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.planificadas.LocationLiveDataBusFragment
import biz.belcorp.salesforce.modules.developmentpath.utils.onLocationUpdated
import com.google.android.gms.maps.model.LatLng
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import kotlinx.android.synthetic.main.fragment_mi_ruta_busqueda.*

class BusquedaFragment : LocationLiveDataBusFragment(), BusquedaView {

    override fun getLayout() = R.layout.fragment_mi_ruta_busqueda

    private val alClickearPersonaListener by lazy { EjecutorAccionesSobreVisita(this) }

    override var listener: BusquedaView.Listener? = null

    private lateinit var adapter: SectionedRecyclerViewAdapter

    private val presenter by injectScoped<RddPresenter>(MI_RUTA_SCOPE, RDD_PRESENTER)

    private val firebaseAnalyticsPresenter: FirebaseAnalyticsPresenter by injectFragment()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter.busquedaView = this

        adapter = SectionedRecyclerViewAdapter()
        rv_main?.layoutManager = LinearLayoutManager(context)
        rv_main?.adapter = adapter

        btn_limpiar?.setOnClickListener { tv_filtro?.text?.clear() }

        tv_filtro?.addTextChangedListener(obtenerTextChangeListener())

        presenter.cargarBusqueda()
        presenter.cargarSugerencias()
    }

    private fun obtenerTextChangeListener() = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) = Unit

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) = Unit

        override fun onTextChanged(
            s: CharSequence?,
            start: Int,
            before: Int, count: Int
        ) {
            ocultarBotonLimpiarSiVacio(s)
            presenter.buscar(s?.toString().orEmpty())
        }

        private fun ocultarBotonLimpiarSiVacio(s: CharSequence?) {
            if (s.isNullOrBlank()) {
                btn_limpiar?.gone()
            } else {
                btn_limpiar?.visible()
            }
        }
    }

    override fun cargar(secciones: List<BusquedaViewModel.Seccion>) {
        adapter.removeAllSections()

        secciones.forEach {
            val titulo = obtenerTitulo(it)
            val icono = obtenerIcono(it)
            val seccion = SeccionMiRutaCards(
                context ?: return,
                titulo,
                icono,
                alClickearPersonaListener, firebaseAnalyticsPresenter
            )

            adapter.addSection(titulo, seccion)
            seccion.actualizar(it.personas)
        }

        adapter.notifyDataSetChanged()
    }

    override fun clearSearchBar() {
        tv_filtro?.text?.clear()
    }

    override fun cargarSugerencias(sugerencias: List<String>) {
        val adapter = ArrayAdapter(
            context
                ?: return, android.R.layout.simple_dropdown_item_1line, sugerencias
        )
        tv_filtro?.setAdapter(adapter)
    }

    private fun obtenerTitulo(seccion: BusquedaViewModel.Seccion): String? {
        return when (seccion.tipoTituloSeccion) {
            BusquedaViewModel.TipoTituloSeccion.FECHA ->
                seccion.fecha
            BusquedaViewModel.TipoTituloSeccion.NO_PLANIFICADAS ->
                context?.getString(R.string.mi_ruta_header_seccion_no_programadas)
            BusquedaViewModel.TipoTituloSeccion.CANDIDATAS_PROACTIVAS ->
                context?.getString(R.string.mi_ruta_header_seccion_candidatas_proactivas)
            BusquedaViewModel.TipoTituloSeccion.CANDIDATAS_NO_PROACTIVAS ->
                context?.getString(R.string.mi_ruta_header_seccion_candidatas_no_proactivas)
        }
    }

    private fun obtenerIcono(seccion: BusquedaViewModel.Seccion): Int? {
        return when (seccion.tipoTituloSeccion) {
            BusquedaViewModel.TipoTituloSeccion.FECHA ->
                null
            BusquedaViewModel.TipoTituloSeccion.NO_PLANIFICADAS ->
                R.drawable.ic_rdd_no_programadas
            BusquedaViewModel.TipoTituloSeccion.CANDIDATAS_PROACTIVAS ->
                R.drawable.ic_rdd_postulantes
            BusquedaViewModel.TipoTituloSeccion.CANDIDATAS_NO_PROACTIVAS ->
                R.drawable.ic_rdd_postulantes
        }
    }

    override fun onConsultantLocationUpdated(consultantId: Int, location: LatLng) {
        adapter.onLocationUpdated(id = consultantId, location = location)
    }

    override fun ocultarResultados() {
        rv_main?.gone()
    }

    override fun ocultarVacio() {
        sv_empty?.gone()
    }

    override fun mostrarVacio() {
        sv_empty?.visible()
    }

    override fun mostrarResultados() {
        rv_main?.visible()
    }
}

