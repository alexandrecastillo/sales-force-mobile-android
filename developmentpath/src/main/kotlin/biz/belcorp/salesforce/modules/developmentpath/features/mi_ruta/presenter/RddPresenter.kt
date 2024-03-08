package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.presenter

import biz.belcorp.salesforce.core.events.EventSubject
import biz.belcorp.salesforce.core.features.handlers.observers.BaseObserver
import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.isSE
import biz.belcorp.salesforce.core.utils.uiThread
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.buscador.BuscarPersonasCercaUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.ruta.RddUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.detalle.view.DetalleEventoView
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.BusquedaViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.ListaMarcadores
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.RddViewModel
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.presenter.mapper.*
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.CabeceraView
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.ContainerRddView
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.busqueda.BusquedaView
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.calendario.CalendarioView
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.mapa.MapaBaseView
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.mapa.MapaView
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.no_planificadas.NoPlanificadasView
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.view.planificadas.PlanificadasView
import biz.belcorp.salesforce.modules.developmentpath.utils.personasACargo

class RddPresenter(
    private val rddUseCase: RddUseCase,
    private val buscarPersonasCercaUseCase: BuscarPersonasCercaUseCase,
    private val rddMapper: RddMapper,
    private val cabeceraMapper: CabeceraMapper,
    private val calendarioMapper: CalendarioMapper,
    private val buscadorMapper: BuscadorMapper,
    private val personaEnMapaMapper: PersonaEnMapaMapper
) {

    var cabeceraView: CabeceraView? = null
    var calendarioView: CalendarioView? = null
    var planificadasView: PlanificadasView? = null
    var noPlanificadasView: NoPlanificadasView? = null
    var detalleEventoView: DetalleEventoView? = null
    var busquedaView: BusquedaView? = null
    var containerRddView: ContainerRddView? = null
    private var planId: Long = -1

    var mapaView: MapaView? = null
    var mapaUbicarView: MapaBaseView? = null

    private var viewModel: RddViewModel? = null
    private var response: RddUseCase.Response? = null

    fun establecerPlanId(planId: Long) {
        this.planId = planId
    }

    fun recalcularPlan() {
        val request = RddUseCase.Request(
            planId,
            RddSubscriber()
        )

        rddUseCase.planificar(request)
    }

    private fun cargarCalendario() {
        viewModel?.apply {
            calendarioView?.cargar(this.calendarioViewModel)
        }
    }

    fun cargarCabecera() {
        viewModel?.let {
            cabeceraView?.cargar(it.cabeceraViewModel)
        }
    }

    fun cargarPlanificadas() {
        viewModel?.apply {
            doAsync {
                val planificadasViewModel =
                    rddMapper.obtenerPlanificadasViewModel(response ?: return@doAsync)
                uiThread {
                    planificadasView?.cargar(planificadasViewModel)
                }
            }
        }

    }

    fun cargarNoPlanificadas() {
        viewModel?.apply {
            noPlanificadasView?.cargar(this.noPlanificadasViewModel)

            if (this.noPlanificadasViewModel.postulantesProactivas.isEmpty()) {
                noPlanificadasView?.ocultarPostulantesProactivas()
            } else {
                noPlanificadasView?.mostrarPostulantesProactivas()
            }

            if (this.noPlanificadasViewModel.postulantesNoProactivas.isEmpty()) {
                noPlanificadasView?.ocultarPostulantesNoProactivas()
            } else {
                noPlanificadasView?.mostrarPostulantesNoProactivas()
            }
        }
    }

    fun cargarMapa() {
        viewModel?.apply {
            mapaView?.cargar(this.personasEnMapaViewModel)
            mapaUbicarView?.cargar(this.personasEnMapaViewModel)
        }
    }

    fun buscarPersonasCerca(marcador: ListaMarcadores) {
        val ubicacion = marcador.recuperarUbicacion()
        if (ubicacion != null) {
            buscarPersonasCercaUseCase.buscar(
                planId = planId,
                personas = response?.rdd?.personasPlanificadas ?: emptyList(),
                ubicacion = ubicacion.convertirAPair(),
                observer = BuscarPersonasCercaSubscriber()
            )
        } else {
            mapaView?.mostrarMensaje("No se ha podido recuperar la ubicación actual.")
        }
    }

    private fun actualizarModeloCalendario() {
        viewModel?.calendarioViewModel = calendarioMapper.map(response?.rdd ?: return)
    }

    private fun actualizarModeloCabecera() {
        viewModel?.cabeceraViewModel = cabeceraMapper.map(response ?: return)
    }

    private fun actualizarModeloPlanificadas() {
        viewModel?.planificadasViewModel =
            rddMapper.obtenerPlanificadasViewModel(response ?: return)
    }

    fun seleccionarHoy() {
        doAsync {
            response?.rdd?.mesSeleccionado?.seleccionarHoy()
            actualizarModeloCalendario()
            actualizarModeloPlanificadas()
            uiThread {
                cargarCalendario()
                cargarPlanificadas()
            }
        }
    }

    fun seleccionar(indice: Int) {
        val dias = response?.rdd?.mesSeleccionado?.dias
        val perteneceAMes = dias?.getOrNull(indice)?.perteneceAMes ?: false
        if (!perteneceAMes) return

        doAsync {
            response?.rdd?.seleccionarDia(indice)
            actualizarModeloCalendario()
            uiThread {
                cargarCalendario()
                cargarPlanificadas()
            }
        }
    }

    fun mesSiguiente() {
        response?.rdd?.mesSiguiente()

        actualizarModeloCabecera()
        actualizarModeloCalendario()
        cargarCabecera()
        cargarCalendario()
    }

    fun mesAnterior() {
        response?.rdd?.mesAnterior()

        actualizarModeloCabecera()
        actualizarModeloCalendario()
        cargarCabecera()
        cargarCalendario()
    }

    fun cargarSugerencias() {
        viewModel?.apply {
            busquedaView?.cargarSugerencias(this.busquedaViewModel.sugerencias)
        }
    }

    fun buscar(filtro: String = "") {
        viewModel?.apply {
            val resultado = response?.rdd?.buscador?.buscar(filtro)

            this.busquedaViewModel.secciones = buscadorMapper.map(resultado ?: return)
            cargarBusqueda()
        }
    }

    fun cargarBusqueda() {
        viewModel?.apply {
            val resultado = this.busquedaViewModel.secciones

            busquedaView?.cargar(resultado)

            mostrarMensajeVacio(resultado)
        }
    }

    fun clearSearchBar() {
        busquedaView?.clearSearchBar()
    }

    private fun mostrarMensajeVacio(resultado: List<BusquedaViewModel.Seccion>) {
        if (resultado.isEmpty()) {
            busquedaView?.mostrarVacio()
            busquedaView?.ocultarResultados()
        } else {
            busquedaView?.mostrarResultados()
            busquedaView?.ocultarVacio()
        }
    }


    private inner class RddSubscriber : BaseObserver<RddUseCase.Response>() {

        override fun onComplete() = Unit

        override fun onError(exception: Throwable) {
            exception.printStackTrace()
        }

        override fun onNext(t: RddUseCase.Response) {
            doAsync {
                response = t
                viewModel = rddMapper.map(response ?: return@doAsync)
                uiThread {
                    setupObservers()
                    cargarCabecera()
                    seleccionarHoy()
                    cargarMapa()
                    cargarNoPlanificadas()
                    cargarSugerencias()
                    cargarBusqueda()
                    clearSearchBar()
                    ocularSnackbar()
                }
            }
        }
    }

    private fun setupObservers() {
        val role = response?.infoPlanRdd?.rol ?: return
        val subjects = mutableListOf<EventSubject>()
        when {
            role.isSE() -> {
                subjects.add(EventSubject.CONSULTANTS_SYNC)
            }
        }
        if (subjects.isNotEmpty()) {
            containerRddView?.setupSyncObservers(subjects.toTypedArray())
        }
    }

    private fun ocularSnackbar() {
        containerRddView?.ocultarSnackbar()
    }


    private inner class BuscarPersonasCercaSubscriber :
        BaseObserver<BuscarPersonasCercaUseCase.Response>() {

        override fun onComplete() = Unit

        override fun onError(exception: Throwable) = Unit

        override fun onNext(t: BuscarPersonasCercaUseCase.Response) {
            if (t.personasCerca.isNotEmpty()) {
                mapaView?.mostrarPersonasCerca(personaEnMapaMapper.map(t.personasCerca))
            } else {
                mapaView?.mostrarMensaje(
                    String.format("No se han encontrado %s cerca.", t.rol.personasACargo())
                )
            }
        }
    }

}
