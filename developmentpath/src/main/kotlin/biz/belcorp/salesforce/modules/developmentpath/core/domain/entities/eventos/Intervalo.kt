package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos

import java.util.*

class Intervalo(
    val inicio: Calendar,
    val fin: Calendar
) : Comparable<Intervalo> {

    val duracion: Long get() = fin.timeInMillis - inicio.timeInMillis
    val sinCruce: Boolean get() = fin >= inicio
    private val peso: Long get() = inicio.timeInMillis + fin.timeInMillis

    fun redondearHoras() {
        inicio.set(Calendar.MINUTE, 0)
        inicio.set(Calendar.SECOND, 0)
        inicio.set(Calendar.MILLISECOND, 0)

        if (fin.get(Calendar.MINUTE) > 0 ||
            fin.get(Calendar.SECOND) > 0 ||
            fin.get(Calendar.MILLISECOND) > 0
        ) {

            fin.add(Calendar.HOUR, 1)
            fin.set(Calendar.MINUTE, 0)
            fin.set(Calendar.SECOND, 0)
            fin.set(Calendar.MILLISECOND, 0)
        }
    }

    override fun compareTo(other: Intervalo): Int {
        return when {
            peso < other.peso -> -1
            peso > other.peso -> 1
            else -> 0
        }
    }

    companion object {
        fun crear(inicio: Calendar, duracion: Long): Intervalo {
            val inicioIntervalo = inicio.clone() as Calendar
            val finIntervalo = inicioIntervalo.clone() as Calendar
            finIntervalo.timeInMillis = finIntervalo.timeInMillis + duracion

            return Intervalo(inicioIntervalo, finIntervalo)
        }
    }
}
