package biz.belcorp.salesforce.modules.consultants.core.data.repository.filtros

import biz.belcorp.salesforce.core.domain.entities.search.TipoEstado
import biz.belcorp.salesforce.modules.consultants.core.domain.repository.filtros.TipoEstadoRepository
import io.reactivex.Observable

class TipoEstadoDataRepository(
    private val dataStore: TipoEstadoDBDataStore,
    private val dataMapper: TipoEstadoEntityDataMapper
) : TipoEstadoRepository {

    override val all: Observable<List<TipoEstado>>
        get() {
            return dataStore
                .all
                .map { dataMapper.parse(it) }
        }

}
