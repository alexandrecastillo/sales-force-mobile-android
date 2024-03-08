package biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.presenter.mapper

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.observaciones.ObservacionVisita
import biz.belcorp.salesforce.modules.developmentpath.features.mi_ruta.model.ObservacionVisitaModel

class ObservacionesVisitaMapper {
    fun map(observacionVisita: ObservacionVisita): ObservacionVisitaModel {
        return ObservacionVisitaModel(
                observacionVisita.id,
                observacionVisita.descripcion ?: "")
    }

    fun map(observaciones: List<ObservacionVisita>): List<ObservacionVisitaModel> {
        return observaciones.map { map(it) }
    }
}
