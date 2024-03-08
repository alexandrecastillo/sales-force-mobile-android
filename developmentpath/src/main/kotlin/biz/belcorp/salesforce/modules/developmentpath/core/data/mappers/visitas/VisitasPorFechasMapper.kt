package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.visitas

import biz.belcorp.salesforce.core.entities.sql.path.VisitaXFechaRDDEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.toDateFromShort
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.VisitasPorFecha
import java.util.*

class VisitasPorFechasMapper {

    fun parse(model: VisitaXFechaRDDEntity): VisitasPorFecha {
        val calendar = Calendar.getInstance()
        model.fechaVisita.toDateFromShort()?.let { calendar.time = it }

        return VisitasPorFecha(
            model.planId,
            calendar,
            model.cantidadVisita
        )
    }

    fun parse(models: List<VisitaXFechaRDDEntity>): List<VisitasPorFecha> {
        return models.map { parse(it) }
    }
}
