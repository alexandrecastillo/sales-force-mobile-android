package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.observaciones

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.observaciones.ObservacionVisita

interface ObservacionVisitaRepository {
    fun obtener(): List<ObservacionVisita>
}
