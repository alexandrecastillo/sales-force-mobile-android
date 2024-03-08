package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.observaciones

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.observaciones.ObservacionVisitaDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.observaciones.ObservacionVisita
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.observaciones.ObservacionVisitaRepository

class ObservacionVisitaDataRepository(private val dbStore: ObservacionVisitaDBDataStore) :
    ObservacionVisitaRepository {
    override fun obtener(): List<ObservacionVisita> {
        return dbStore.obtener()
    }
}
