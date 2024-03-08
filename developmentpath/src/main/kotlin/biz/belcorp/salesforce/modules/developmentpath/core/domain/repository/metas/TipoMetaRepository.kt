package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.metas

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.metas.TipoMeta
import io.reactivex.Single

interface TipoMetaRepository {
    fun obtener(): Single<List<TipoMeta>>
}
