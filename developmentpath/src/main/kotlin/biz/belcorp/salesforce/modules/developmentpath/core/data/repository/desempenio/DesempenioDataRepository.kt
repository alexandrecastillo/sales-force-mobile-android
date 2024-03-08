package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.desempenio

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.desempenio.DesempenioDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.desempenio.DesempenioCampanias
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.desempenio.DesempenioRepository

class DesempenioDataRepository(private val dbStore: DesempenioDBDataStore) : DesempenioRepository {

    override fun obtener(llaveUA: LlaveUA): List<DesempenioCampanias> {
        return dbStore.obtener(llaveUA)
    }
}
