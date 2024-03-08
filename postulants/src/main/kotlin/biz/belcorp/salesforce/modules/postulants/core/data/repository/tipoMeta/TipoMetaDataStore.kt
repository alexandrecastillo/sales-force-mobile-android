package biz.belcorp.salesforce.modules.postulants.core.data.repository.tipoMeta

import biz.belcorp.salesforce.core.entities.sql.filtros.TipoMetaEntity
import io.reactivex.Single

interface TipoMetaDataStore {
    fun obtener(): Single<List<TipoMetaEntity>>
}
