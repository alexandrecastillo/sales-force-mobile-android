package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas

import biz.belcorp.salesforce.core.utils.es
import biz.belcorp.salesforce.core.utils.toCalendar
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd

import java.io.Serializable
import java.util.*

class AgendaVisitas(val visitas: List<Visita>) : Serializable {

    companion object {
        private const val MAXIMO_VISITAS_ADICIONALES = 2
    }

    private val tieneAlMenosUnaProgramada get() = visitas.any { it.estaProgramada }

    val tieneAlMenosUnaRegistrada get() = visitas.any { it.estaRegistrada }

    fun tieneAlMenosUnaRegistradaEn(fecha: Date) =
        visitas.any { it.fechaRegistro.es(fecha.toCalendar()) }

    val tieneAlMenosUnaRegistrable get() = visitas.any { it.esRegistrable }

    val visitasRegistradas get() = visitas.filter { it.estaRegistrada }

    private val tieneAlMenosUnaReprogramada get() = visitas.any { it.estaReprogramada }

    val estaNoPlanificada get() = !planificada

    val planificada
        get() = tieneAlMenosUnaProgramada ||
            tieneAlMenosUnaRegistrada ||
            tieneAlMenosUnaReprogramada

    val todasRegistradas get() = visitas.all { it.estaRegistrada }

    val primeraNoRegistrada
        get() = visitas
            .sortedWith(compareBy(nullsLast<Calendar>()) { it.fechaAMostrar })
            .find { !it.estaRegistrada }

    val permiteAdicionarVisitas
        get(): Boolean {
            return todasRegistradas &&
                visitas.count { it.esAdicional } < MAXIMO_VISITAS_ADICIONALES
        }

    val ultimaRegistrada
        get() = visitas.filter { it.estaRegistrada }
            .sortedByDescending { it.fechaRegistro }
            .firstOrNull()

    fun establecerPersona(persona: PersonaRdd) {
        visitas.forEach { it.establecerPersona(persona) }
    }
}
