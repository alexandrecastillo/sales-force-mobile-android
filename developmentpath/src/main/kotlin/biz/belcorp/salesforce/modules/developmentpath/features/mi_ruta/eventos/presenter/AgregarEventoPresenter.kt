package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.presenter

import biz.belcorp.salesforce.core.utils.aumentarMinutosSinCambiarDia
import biz.belcorp.salesforce.core.utils.inicioDelDia
import biz.belcorp.salesforce.core.utils.redondearMinutosHaciaArriba
import biz.belcorp.salesforce.core.utils.toCalendar
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.eventos.CrearEventoUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.eventos.EditarEventoUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.eventos.RecuperarDatosAgregarEventoUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.AgregarEventoView
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.model.AgregarEventoModelMapper
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.model.AgregarEventoViewModel
import java.util.*

class AgregarEventoPresenter(recuperarTiposEventoUseCase: RecuperarDatosAgregarEventoUseCase,
                             crearEventoUseCase: CrearEventoUseCase,
                             editarEventoUseCase: EditarEventoUseCase,
                             modelMapper: AgregarEventoModelMapper) {

    var view: AgregarEventoView? = null
        set(value) {
            field = value
            viewHandler.view = value
        }

    private val model = AgregarEventoViewModel()

    private val viewHandler = AgregarEventoViewHandler(view, model)

    private val recuperarDatosHandler = RecuperarDatosHandler(viewHandler,
                                                              model,
                                                              modelMapper,
                                                              recuperarTiposEventoUseCase)

    private val guardarEventoHandler = GuardarEventoHandler(viewHandler,
                                                            model,
                                                            modelMapper,
                                                            crearEventoUseCase,
                                                            editarEventoUseCase)

    fun configurarFechaInicial(fecha: Date) {
        if (fecha.before(Date())) {
            configurarFechasAPartirDeAhora()
        } else {
            configurarFechasPorDefecto(fecha)
        }
    }

    private fun configurarFechasAPartirDeAhora() {
        model.fecha = Date()

        model.horaInicio = model.fecha
                .aumentarMinutosSinCambiarDia(15)
                .redondearMinutosHaciaArriba(5)

        model.horaFin = model.fecha
                .aumentarMinutosSinCambiarDia(30)
                .redondearMinutosHaciaArriba(5)
    }

    private fun configurarFechasPorDefecto(fecha: Date) {
        model.fecha = fecha

        val inicio = model
                .fecha
                .toCalendar()
                .inicioDelDia()

        val fin = model
                .fecha
                .toCalendar()
                .inicioDelDia()

        /* Valores arbitrarios*/
        inicio.set(Calendar.HOUR_OF_DAY, 8)
        fin.set(Calendar.HOUR_OF_DAY, 10)

        model.horaInicio = inicio.time
        model.horaFin = fin.time
    }

    fun cargarDatosParaCreacion(planId: Long) {
        recuperarDatosHandler.recuperarUsandoPlan(planId)
    }

    fun cargarDatosParaEdicion(eventoId: Long) {
        recuperarDatosHandler.recuperarUsandoEvento(eventoId)
    }

    fun seleccionarTipoEvento(posicion: Int) {
        recuperarDatosHandler.seleccionarTipoEvento(posicion)
    }

    fun cambiarValorSwitchTodoElDia(valor: Boolean) {
        model.esTodoElDia = valor
        model.mostrarHoras = !valor
        viewHandler.mostrarUOcultarHoras()
    }

    fun cambiarDescripcionPersonalizada(valor: String) {
        model.descripcionPersonalizada = valor
        model.botonGuardarHabilitado = !model.mostrarDescripcionPersonalizada ||
                model.descripcionPersonalizada.isNotBlank()
        viewHandler.habilitarODeshabilitarBotonGuardar()
    }

    fun cambiarUbicacion(valor: String) {
        model.ubicacion = valor
    }

    fun cambiarValorCheckboxCompartir(valor: Boolean) {
        model.compartir = valor
    }

    fun seleccionarTiempoAlerta(posicion: Int) {
        recuperarDatosHandler.seleccionarAlerta(posicion)
    }

    fun seleccionarFecha(fecha: Date) {
        model.fecha = fecha
        viewHandler.pintarFecha()
    }

    fun seleccionarHora(horaInicio: Date,
                        horaFin: Date) {
        model.horaInicio = horaInicio
        model.horaFin = horaFin

        viewHandler.pintarYConfigurarHoras()
    }

    fun guardar() {
        guardarEventoHandler.crear()
    }

    fun editar() {
        guardarEventoHandler.editar()
    }
}
