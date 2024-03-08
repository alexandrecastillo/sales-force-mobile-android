package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.visitas

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.VisitasPorFecha
import java.util.*

interface VisitasPorFechaRepository {
    fun obtener(planId: Long): List<VisitasPorFecha>
    fun obtener(filtro: Filtro): VisitasPorFecha?
    fun guardar(visitasPorFecha: VisitasPorFecha)
    fun obtenerNoEnviados(): List<VisitasPorFecha>
    fun actualizarNoEnviados()

    class Filtro(val planId: Long, val fecha: Date)
}
