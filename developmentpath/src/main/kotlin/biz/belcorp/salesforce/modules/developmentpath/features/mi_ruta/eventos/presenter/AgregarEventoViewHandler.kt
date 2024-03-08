package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.presenter

import biz.belcorp.salesforce.core.utils.parseToHoursDescription
import biz.belcorp.salesforce.core.utils.parseToShortDescripitionDayMonth
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.AgregarEventoView
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.model.AgregarEventoViewModel

class AgregarEventoViewHandler(var view: AgregarEventoView?,
                               private val model: AgregarEventoViewModel) {

    fun pintarDescripcionPersonalizada() {
        view?.pintarDescripcionPersonalizada(model.descripcionPersonalizada)
    }

    fun pintarSwitchTodoElDia() {
        if (model.esTodoElDia) {
            view?.activarSwitchTodoElDia()
        } else {
            view?.desactivarSwitchTodoElDia()
        }
    }

    fun pintarUbicacion() {
        view?.pintarUbicacion(model.ubicacion)
    }

    fun pintarFecha() {
        view?.pintarFecha(model.fecha.parseToShortDescripitionDayMonth())
        view?.configurarFecha(model.fecha)
    }

    fun mostrarUOcultarHoras() {
        if (model.mostrarHoras) {
            view?.mostrarHoras()
        } else {
            view?.ocultarHoras()
        }
    }

    fun pintarYConfigurarHoras() {
        view?.pintarHoras("${model.horaInicio.parseToHoursDescription()} -" +
                " ${model.horaFin.parseToHoursDescription()}")
        view?.configurarHoras(model.horaInicio, model.horaFin)
    }

    fun pintarTiposEvento() {
        view?.pintarTiposEvento(model.tiposEvento.map { it.descripcion })
    }

    fun pintarTiemposAlerta() {
        view?.pintarTiemposAlerta(model.tiemposAlerta)
    }

    fun pintarTiempoAlertaSeleccionado() {
        view?.pintarTiempoAlertaSeleccionado(model.alertaSeleccionada ?: return)
    }

    fun pintarTipoEventoSeleccionado() {
        view?.pintarTipoEventoSeleccionado(model.indiceTipoEventoSeleccionado)
    }

    fun mostrarUOcultarDescripcionPersonalizada() {
        if (model.mostrarDescripcionPersonalizada) {
            view?.mostrarDescripcionPersonalizada()
        } else {
            view?.ocultarDescripcionPersonalizada()
        }
    }

    fun pintarAvisoCompartir() {
        view?.pintarTextoAvisoCompartir(model.rolCreador ?: return)
    }

    fun mostrarUOcultarAvisoCompartir() {
        if (model.mostrarAvisoCompartir) {
            view?.mostrarAvisoCompartir()
        } else {
            view?.ocultarAvisoCompartir()
        }
    }

    fun pintarTextoCheckboxCompartir() {
        view?.pintarTextoCheckboxCompartir(model.rolCreador ?: return)
    }

    fun pintarLayoutCompartir() {
        if (model.mostrarCompartir) {
            view?.mostrarLayoutCompartir()
        } else {
            view?.ocultarLayoutCompartir()
        }
    }

    fun pintarCompartir() {
        if (model.compartir) view?.activarCompartir()
        else view?.desactivarCompartir()
    }

    fun habilitarODeshabilitarBotonGuardar() {
        if (model.botonGuardarHabilitado) view?.habilitarBotonGuardar()
        else view?.deshabilitarBotonGuardar()
    }

    fun mostrarMensajeExito() {
        view?.mostrarMensajeExito()
    }

    fun mostrarErrorGenerico() {
        view?.mostrarErrorGenerico()
    }

    fun mostrarErrorFechaFueraDePeriodo() {
        view?.mostrarErrorFechaFueraDePeriodo()
    }

    fun mostrarErrorFechaAntesAHoy() {
        view?.mostrarErrorFechaAntesAHoy()
    }

    fun mostrarErrorHoraInicioMayorAFin() {
        view?.mostrarErrorHoraInicioMayorAFin()
    }

    fun notificarCambioEnPlanificacion() {
        view?.emitirBroadcast()
    }

    fun cerrarPantalla() {
        view?.cerrar()
    }

    fun actualizarAlEditadoEvento() {
        view?.emitirBroadcastEventoEditado()
    }
}
