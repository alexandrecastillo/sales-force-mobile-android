package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.observaciones

import biz.belcorp.salesforce.core.entities.sql.plan.ObservacionVisitaRDDEntity
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.observaciones.ObservacionVisita

class ObservacionVisitaMapper{
    fun parse(model: ObservacionVisitaRDDEntity): ObservacionVisita {
        return ObservacionVisita(
            model.id,
            model.rol,
            model.descripcion)
    }

    fun parse(modelos: List<ObservacionVisitaRDDEntity>): List<ObservacionVisita> {
        return modelos.map { parse(it) }
    }
}
