package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.plan

import biz.belcorp.salesforce.core.utils.finDelDia
import biz.belcorp.salesforce.core.utils.inicioDelDia
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.Intervalo
import java.util.*

class PlanificadorIntervalos(private val fecha: Calendar) {

    private val intervalosPlanificados: SortedSet<Intervalo> = sortedSetOf()
    val intervalosSinCruceAgregados = mutableListOf<Intervalo>()

    init {
        agregarIntervalosTope()
    }

    private fun agregarIntervalosTope() {
        val inicioDelDia = fecha.inicioDelDia()
        val finDelDia = fecha.finDelDia()
        intervalosPlanificados.add(Intervalo(inicioDelDia, inicioDelDia))
        intervalosPlanificados.add(Intervalo(finDelDia, finDelDia))
    }

    fun redondearHorasDeIntervalos() {
        intervalosPlanificados.forEach { it.redondearHoras() }
    }

    fun agregarIntervalos(nuevosIntervalos: List<Intervalo>) {
        intervalosPlanificados.addAll(nuevosIntervalos)
    }

    fun agregarIntervalo(nuevoIntervalo: Intervalo) {
        intervalosPlanificados.add(nuevoIntervalo)
    }

    fun agregarIntervalosSinCruce(duracionintervalo: Long) {
        val primerIntervaloSinCruce = obtenerPrimerIntervaloSinCruce(duracionintervalo)
        if (primerIntervaloSinCruce != null) {
            val nuevoIntervalo = Intervalo.crear(
                primerIntervaloSinCruce.inicio.clone() as Calendar,
                duracionintervalo
            )
            intervalosPlanificados.add(nuevoIntervalo)
            intervalosSinCruceAgregados.add(nuevoIntervalo)
        }
    }

    private fun obtenerPrimerIntervaloSinCruce(duracion: Long): Intervalo? {
        for ((indice, intervaloPlanificadoActual) in intervalosPlanificados.withIndex()) {
            if (indice >= intervalosPlanificados.size - 1) return null
            val intervaloPlanificadoSiguiente = obtenerSiguientePlanificado(indice)
            val intervaloEntrePlanificados = Intervalo(
                inicio = intervaloPlanificadoActual.fin,
                fin = intervaloPlanificadoSiguiente.inicio
            )
            if (noHayCruceYHayEspacio(intervaloEntrePlanificados, duracion)) {
                return intervaloEntrePlanificados
            }
        }
        return null
    }

    private fun noHayCruceYHayEspacio(
        intervaloEntrePlanificados: Intervalo,
        duracion: Long
    ): Boolean {
        return duracion <= intervaloEntrePlanificados.duracion
    }

    private fun obtenerSiguientePlanificado(indice: Int): Intervalo {
        val indiceSiguiente = indice + 1
        return intervalosPlanificados.elementAt(indiceSiguiente)
    }
}
