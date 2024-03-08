package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.metas.MetaConsultora
import io.reactivex.Completable
import io.reactivex.Single

interface MetaConsultoraRepository {
    fun guardar(metaConsultora: MetaConsultora): Completable
    fun obtenerMeta(consultoraId: Int): Single<List<MetaConsultora>>
    fun sincronizar(): Completable
}
