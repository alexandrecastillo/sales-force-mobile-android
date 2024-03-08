package biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas

import java.util.*

class VisitasPorFecha(
    val planId: Long,
    val fecha: Calendar,
    var visitas: Int
) {

    private fun aumentar(cantidad: Int) {
        visitas += cantidad
        if (visitas < 0) visitas = 0
    }

    fun aumentarUno() {
        aumentar(1)
    }

    fun disminuirUno() {
        aumentar(-1)
    }

    companion object {
        fun crear(planId: Long, fecha: Date, visitas: Int): VisitasPorFecha {
            val calendar = Calendar.getInstance()
            calendar.time = fecha
            return VisitasPorFecha(planId, calendar, visitas)
        }
    }
}
