package biz.belcorp.salesforce.modules.developmentpath.features.dashboard.avance.visitas.model

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.AvanceVisitas

class AvanceMapper {

    fun map(avance: AvanceVisitas): AvanceModel {
        return AvanceModel(
                cantidadPlanificadas = avance.planificadas,
                cantidadVisitadas = avance.visitadas,
                porcentaje = avance.progreso.toInt(),
                tipoHijas = avance.tipoHijas)
    }
}
