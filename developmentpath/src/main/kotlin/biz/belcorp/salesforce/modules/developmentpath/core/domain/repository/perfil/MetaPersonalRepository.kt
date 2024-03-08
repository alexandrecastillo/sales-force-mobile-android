package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.metas.MetaPersonal
import io.reactivex.Completable
import io.reactivex.Single

interface MetaPersonalRepository {
    fun listar(personaId: Long): Single<List<MetaPersonal>>
    fun guardar(meta: MetaPersonal): Single<MetaPersonal>
    fun eliminar(meta: MetaPersonal): Completable
    fun actualizar(meta: MetaPersonal): Single<MetaPersonal>
    fun sincronizar(): Completable
}
