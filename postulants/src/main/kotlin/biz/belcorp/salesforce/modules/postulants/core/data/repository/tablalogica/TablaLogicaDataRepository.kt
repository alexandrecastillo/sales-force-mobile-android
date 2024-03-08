package biz.belcorp.salesforce.modules.postulants.core.data.repository.tablalogica

import biz.belcorp.salesforce.modules.postulants.core.domain.entities.unete.TablaLogica
import biz.belcorp.salesforce.modules.postulants.core.domain.repository.TablaLogicaRepository
import io.reactivex.Single

class TablaLogicaDataRepository(
    private val dbStore: TablaLogicaDBStore,
    private val mapper: TablaLogicaEntityDataMapper
) : TablaLogicaRepository {

    override fun list(tipoParametro: Int): Single<List<TablaLogica>> {
        return dbStore.list(tipoParametro).map {
            mapper.reverseMap(it)
        }
    }

}
