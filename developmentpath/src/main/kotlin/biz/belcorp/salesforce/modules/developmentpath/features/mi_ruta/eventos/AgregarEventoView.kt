package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.model.AgregarEventoViewModel
import java.util.*

interface AgregarEventoView {
    fun pintarTiposEvento(tiposEvento: List<String>)
    fun pintarTipoEventoSeleccionado(indice: Int)
    fun pintarFecha(fecha: String)
    fun configurarFecha(fecha: Date)
    fun pintarDescripcionPersonalizada(descripcion: String)
    fun activarSwitchTodoElDia()
    fun desactivarSwitchTodoElDia()
    fun pintarUbicacion(ubicacion: String)
    fun mostrarHoras()
    fun ocultarHoras()
    fun pintarHoras(horas: String)
    fun configurarHoras(inicio: Date, fin: Date)
    fun pintarTiemposAlerta(alertas: List<AgregarEventoViewModel.Alerta>)
    fun pintarTiempoAlertaSeleccionado(alerta: AgregarEventoViewModel.Alerta)
    fun pintarTextoAvisoCompartir(rol: Rol)
    fun mostrarDescripcionPersonalizada()
    fun ocultarDescripcionPersonalizada()
    fun mostrarAvisoCompartir()
    fun ocultarAvisoCompartir()
    fun pintarTextoCheckboxCompartir(rol: Rol)
    fun mostrarLayoutCompartir()
    fun ocultarLayoutCompartir()
    fun activarCompartir()
    fun desactivarCompartir()
    fun mostrarMensajeExito()
    fun habilitarBotonGuardar()
    fun deshabilitarBotonGuardar()
    fun mostrarErrorGenerico()
    fun mostrarErrorFechaFueraDePeriodo()
    fun mostrarErrorFechaAntesAHoy()
    fun mostrarErrorHoraInicioMayorAFin()
    fun emitirBroadcast()
    fun emitirBroadcastEventoEditado()
    fun cerrar()
}
