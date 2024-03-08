package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.metas

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.metas.MetaSocia
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import io.reactivex.Completable
import io.reactivex.Single

interface MetasSociaRepository {
    fun guardar(meta: MetaSocia): Completable
    fun listar(persona: PersonaRdd): Single<List<MetaSocia>>
    fun eliminar(meta: MetaSocia): Completable
    fun actualizar(meta: MetaSocia): Completable
}
