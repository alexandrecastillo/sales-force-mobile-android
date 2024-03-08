package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.eventos.model

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.aumentarMinutosSinCambiarDia
import biz.belcorp.salesforce.core.utils.redondearMinutosHaciaArriba
import java.util.*

data class AgregarEventoViewModel(var id: Long? = null,
                                  var tiposEvento: List<TipoEvento> = emptyList(),
                                  var tiemposAlerta: List<Alerta> = emptyList()) {

    var descripcionPersonalizada = ""
    var ubicacion = ""
    var mostrarDescripcionPersonalizada = false
    var mostrarAvisoCompartir = false
    var mostrarCompartir = false
    var compartir = false
    var compartirOriginal = false
    var esTodoElDia = false
    var mostrarHoras = true
    var botonGuardarHabilitado = false
    var eventoRegistrado = false

    var fecha = Date()

    var horaInicio = Date()
            .aumentarMinutosSinCambiarDia(15)
            .redondearMinutosHaciaArriba(5)

    var horaFin = Date()
            .aumentarMinutosSinCambiarDia(30)
            .redondearMinutosHaciaArriba(5)

    var rolCreador: Rol? = null
    var tipoEventoSeleccionado: TipoEvento? = null

    val indiceTipoEventoSeleccionado get() =
        tiposEvento.indexOfFirst { it.id == tipoEventoSeleccionado?.id }

    var alertaSeleccionada: Alerta? = null

    data class TipoEvento(val id: Long,
                          val descripcion: String)

    data class Alerta(val cantidad: Int,
                      val unidad: Unidad,
                      val seleccionado: Boolean)

    enum class Unidad {
        SIN_ALERTA,
        A_LA_HORA,
        MINUTOS,
        HORAS,
        DIAS,
        SEMANAS
    }
}
