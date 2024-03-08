package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.metas

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.metas.data.TipoMetaDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.metas.TipoMetaEntityDataMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.metas.TipoMeta
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.metas.TipoMetaRepository
import io.reactivex.Single

class TipoMetaDataRepository(
    private val dataStore: TipoMetaDBDataStore,
    private val dataMapper: TipoMetaEntityDataMapper
) : TipoMetaRepository {

    override fun obtener(): Single<List<TipoMeta>> {
        return dataStore
            .obtener()
            .map { dataMapper.parse(it) }
    }
}
