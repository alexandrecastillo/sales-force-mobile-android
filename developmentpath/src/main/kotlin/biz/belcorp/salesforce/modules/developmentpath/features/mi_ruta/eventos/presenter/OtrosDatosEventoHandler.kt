package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.presenter

import biz.belcorp.salesforce.core.utils.doAsync
import biz.belcorp.salesforce.core.utils.uiThread
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.EventoRdd
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.model.AgregarEventoViewModel
import java.util.*

class OtrosDatosEventoHandler(private val viewHandler: AgregarEventoViewHandler,
                              private val model: AgregarEventoViewModel) {

    fun manejarRespuesta(respuesta: EventoRdd?) {
        doAsync {

            if (respuesta != null) {
                model.id = respuesta.id
                model.ubicacion = respuesta.ubicacion
                model.fecha = respuesta.fechaInicio.time
                model.horaInicio = respuesta.fechaInicio.time
                model.horaFin = respuesta.fechaFin?.time ?: Date()
                model.descripcionPersonalizada = respuesta.descripcionPersonalizada ?: ""
                model.esTodoElDia = respuesta.esTodoElDia
                model.compartir = respuesta.compartirObligatorio
                model.compartirOriginal = respuesta.compartirObligatorio
                model.mostrarHoras = !respuesta.esTodoElDia
            }

            uiThread {
                refrescarVista()
            }
        }
    }

    private fun refrescarVista() {
        viewHandler.mostrarUOcultarDescripcionPersonalizada()
        viewHandler.mostrarUOcultarHoras()
        viewHandler.pintarDescripcionPersonalizada()
        viewHandler.pintarSwitchTodoElDia()
        viewHandler.pintarUbicacion()
        viewHandler.pintarFecha()
        viewHandler.pintarYConfigurarHoras()
        viewHandler.pintarCompartir()
        viewHandler.pintarLayoutCompartir()
        viewHandler.habilitarODeshabilitarBotonGuardar()
    }
}
