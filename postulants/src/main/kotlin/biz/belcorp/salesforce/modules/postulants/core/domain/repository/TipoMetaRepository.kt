package biz.belcorp.salesforce.modules.postulants.core.domain.repository

import biz.belcorp.salesforce.modules.postulants.core.domain.entities.tipo.TipoMeta
import io.reactivex.Single

interface TipoMetaRepository {
    fun obtener(): Single<List<TipoMeta>>
}
