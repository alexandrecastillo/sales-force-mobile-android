package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.ruta

import biz.belcorp.salesforce.modules.developmentpath.core.data.network.dto.ruta.VisitasPorFechaRequest
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.toShortString
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.VisitasPorFecha

class VisitasPorFechaMapper {
    private fun parse(visitasPorFecha: VisitasPorFecha): VisitasPorFechaRequest.VisitaXFechaRDD {
        return VisitasPorFechaRequest.VisitaXFechaRDD(
            id = visitasPorFecha.planId,
            fechaVisita = visitasPorFecha.fecha.toShortString(),
            cantidadVisita = visitasPorFecha.visitas
        )
    }

    fun parse(visitasPorFechas: List<VisitasPorFecha>): VisitasPorFechaRequest {
        val visitasXFecha = visitasPorFechas.map { parse(it) }
        return VisitasPorFechaRequest(visitasXFecha)
    }
}
