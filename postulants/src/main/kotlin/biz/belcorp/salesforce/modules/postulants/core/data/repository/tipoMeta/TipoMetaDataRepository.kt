package biz.belcorp.salesforce.modules.postulants.core.data.repository.tipoMeta

import biz.belcorp.salesforce.modules.postulants.core.domain.entities.tipo.TipoMeta
import biz.belcorp.salesforce.modules.postulants.core.domain.repository.TipoMetaRepository
import io.reactivex.Single

class TipoMetaDataRepository(
    private val dataStore: TipoMetaDBDataStore,
    private val dataMapper: TipoMetaEntityDataMapper
) : TipoMetaRepository {

    override fun obtener(): Single<List<TipoMeta>> {
        return dataStore.obtener().map { dataMapper.parse(it) }
    }
}
