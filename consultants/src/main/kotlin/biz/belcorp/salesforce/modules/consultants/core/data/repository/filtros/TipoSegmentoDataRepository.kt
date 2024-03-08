package biz.belcorp.salesforce.modules.consultants.core.data.repository.filtros

import biz.belcorp.salesforce.core.domain.entities.search.TipoSegmento
import biz.belcorp.salesforce.modules.consultants.core.domain.repository.filtros.TipoSegmentoRepository
import io.reactivex.Observable

class TipoSegmentoDataRepository(
    private val dataStore: TipoSegmentoDBDataStore,
    private val dataMapper: TipoSegmentoEntityDataMapper
) : TipoSegmentoRepository {

    override val all: Observable<List<TipoSegmento>>
        get() {
            return dataStore
                .all
                .map { dataMapper.parse(it) }
        }

}
