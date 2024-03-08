package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos

import biz.belcorp.salesforce.core.utils.es
import java.util.*

open class Evento(val fecha: Calendar, val esTodoElDia: Boolean, val registrado: Boolean)

open class Hito(
    val titulo: String?,
    fecha: Calendar
) : Evento(fecha, true, false)

class HitoEnRegion(
    titulo: String?,
    fecha: Calendar,
    val codigoZona: String?
) : Hito(titulo, fecha)

class Feriado(fecha: Calendar) : Evento(fecha, true, false)

class CronogramaEventos(val eventos: List<Evento>) {

    fun obtener(dia: Calendar) =
        eventos.filter { it.fecha.es(dia) }

    fun obtenerEventosRdd(dia: Calendar): List<EventoRdd> =
        obtener(dia).mapNotNull { it as? EventoRdd }

    fun hayEventoTodoElDiaEn(dia: Calendar) =
        eventos.count { it.esTodoElDia && it.fecha.es(dia) } > 0
}
