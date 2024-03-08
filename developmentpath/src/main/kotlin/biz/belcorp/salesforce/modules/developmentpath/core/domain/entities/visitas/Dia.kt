package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas

import biz.belcorp.salesforce.core.utils.perteneceAMes
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.Evento
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.ConsultoraRdd
import java.util.*

data class Dia(
    val fecha: Calendar,
    val visitasProgramadas: List<Visita>,
    val perteneceACampania: Boolean,
    val esInicioCampania: Boolean,
    val esFinCampania: Boolean,
    var esHoy: Boolean,
    var seleccionado: Boolean,
    val eventos: List<Evento>
) {

    var estaEnCampania: Boolean = true

    val existenConsultorasNuevas: Boolean
        get() =
            visitasProgramadas.any { it.persona is ConsultoraRdd && (it.persona as ConsultoraRdd).esNueva }

    val inicioSemana: Boolean
        get() = validate(Calendar.SUNDAY)

    val finSemana: Boolean
        get() = validate(Calendar.SATURDAY)

    fun obtenerDiaDeMes(mes: Calendar): DiaDeMes {
        return DiaDeMes(mes, this)
    }

    private fun validate(day: Int): Boolean {
        return estaEnCampania && fecha.get(Calendar.DAY_OF_WEEK) == day
    }
}

class DiaDeMes(
    private val mes: Calendar,
    val dia: Dia
) {
    val perteneceAMes: Boolean
        get() {
            return dia.fecha.perteneceAMes(mes)
        }
}
