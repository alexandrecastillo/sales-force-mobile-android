package biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.metas.data

import biz.belcorp.salesforce.core.entities.sql.filtros.TipoMetaEntity
import io.reactivex.Single

interface TipoMetaDataStore {
    fun obtener(): Single<List<TipoMetaEntity>>
}
