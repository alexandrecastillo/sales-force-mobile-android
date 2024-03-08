package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.presenter

import biz.belcorp.salesforce.core.features.handlers.observers.BaseCompletableObserver
import biz.belcorp.salesforce.core.utils.Logger
import biz.belcorp.salesforce.core.utils.toCalendar
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.EventoRddModeloCreacion
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.EventoRddModeloEdicion
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.eventos.CambioEventoUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.eventos.CrearEventoUseCase
import biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.eventos.EditarEventoUseCase
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.model.AgregarEventoModelMapper
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.model.AgregarEventoViewModel
import java.util.*

class GuardarEventoHandler(private val viewHandler: AgregarEventoViewHandler,
                           private val model: AgregarEventoViewModel,
                           private val modelMapper: AgregarEventoModelMapper,
                           private val crearEventoUseCase: CrearEventoUseCase,
                           private val editarEventoUseCase: EditarEventoUseCase
) {

    fun crear() {
        val modeloCreacion = EventoRddModeloCreacion(
                idTipoEvento = model.tipoEventoSeleccionado?.id ?: return,
                fechaInicio = aplicarHoraAFechaSeleccionada(model.horaInicio),
                fechaFin = aplicarHoraAFechaSeleccionada(model.horaFin),
                esTodoElDia = model.esTodoElDia,
                descripcionPersonalizada = model.descripcionPersonalizada,
                compartirObligatorio = model.compartir,
                ubicacion = model.ubicacion,
                alertar = model.alertaSeleccionada?.unidad != AgregarEventoViewModel.Unidad.SIN_ALERTA,
                alerta = modelMapper.convertir(model.alertaSeleccionada ?: return))

        crearEventoUseCase.crear(modeloCreacion, GuardarEventoSubscriber())
    }

    fun editar() {
        val modeloEdicion = EventoRddModeloEdicion(
                id = requireNotNull(model.id),
                idTipoEvento = model.tipoEventoSeleccionado?.id ?: return,
                fechaInicio = aplicarHoraAFechaSeleccionada(model.horaInicio),
                fechaFin = aplicarHoraAFechaSeleccionada(model.horaFin),
                esTodoElDia = model.esTodoElDia,
                descripcionPersonalizada = model.descripcionPersonalizada,
                compartirObligatorio = model.compartir,
                ubicacion = model.ubicacion,
                alertar = model.alertaSeleccionada?.unidad != AgregarEventoViewModel.Unidad.SIN_ALERTA,
                alerta = modelMapper.convertir(model.alertaSeleccionada ?: return))

        editarEventoUseCase.editar(modeloEdicion, GuardarEventoSubscriber())
    }

    private fun aplicarHoraAFechaSeleccionada(hora: Date): Calendar {
        val fecha = model.fecha.toCalendar()
        val hora = hora.toCalendar()

        fecha.set(Calendar.HOUR_OF_DAY, hora.get(Calendar.HOUR_OF_DAY))
        fecha.set(Calendar.MINUTE, hora.get(Calendar.MINUTE))
        fecha.set(Calendar.SECOND, hora.get(Calendar.SECOND))
        fecha.set(Calendar.MILLISECOND, hora.get(Calendar.MILLISECOND))

        return fecha
    }

    inner class GuardarEventoSubscriber : BaseCompletableObserver() {
        override fun onComplete() {
            viewHandler.notificarCambioEnPlanificacion()
            viewHandler.mostrarMensajeExito()
            viewHandler.actualizarAlEditadoEvento()
            viewHandler.cerrarPantalla()
        }

        override fun onError(e: Throwable) {
            when (e) {
                is CambioEventoUseCase.FechaFueraDePeriodoException ->
                    viewHandler.mostrarErrorFechaFueraDePeriodo()
                is CambioEventoUseCase.FechaAntesAHoyException ->
                    viewHandler.mostrarErrorFechaAntesAHoy()
                is CambioEventoUseCase.HoraInicioMayorAFinException ->
                    viewHandler.mostrarErrorHoraInicioMayorAFin()
                else -> {
                    Logger.loge("Crear evento", e.localizedMessage, e)
                    viewHandler.mostrarErrorGenerico()
                }
            }
        }
    }
}
