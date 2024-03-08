package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.ruta

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.ruta.data.FechaNoHabilDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.eventos.Feriado
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.ruta.FechaNoHabilRepository

class FechaNoHabilDataRepository(private val dbStore: FechaNoHabilDBDataStore) :
    FechaNoHabilRepository {

    override fun obtener(llaveUA: LlaveUA): List<Feriado> {
        return dbStore.obtener(llaveUA)
    }
}
