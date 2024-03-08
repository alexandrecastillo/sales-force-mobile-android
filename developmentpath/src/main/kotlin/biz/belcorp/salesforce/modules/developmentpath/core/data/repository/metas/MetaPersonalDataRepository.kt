package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.metas

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.anotaciones.cloud.AnotacionCloudStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.anotaciones.data.AnotacionDBStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.metas.MetaDataMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.anotaciones.AnotacionesDataRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.metas.MetaPersonal
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil.MetaPersonalRepository
import io.reactivex.Completable
import io.reactivex.Single

class MetaPersonalDataRepository(storeDB: AnotacionDBStore,
                                 storeCloud: AnotacionCloudStore,
                                 private val mapper: MetaDataMapper
)
    : AnotacionesDataRepository(storeDB, storeCloud), MetaPersonalRepository {


    override fun listar(personaId: Long): Single<List<MetaPersonal>> {
        return storeDB
            .listarMetas(personaId)
            .map { mapper.parse(it) }
    }

    override fun guardar(meta: MetaPersonal): Single<MetaPersonal> {
        return storeDB
            .guardar(mapper.parse(meta))
            .map { mapper.parse(it) }
    }

    override fun eliminar(meta: MetaPersonal): Completable {
        return storeDB.eliminar(mapper.parse(meta))
    }

    override fun actualizar(meta: MetaPersonal): Single<MetaPersonal> {
        return storeDB
            .actualizar(mapper.parse(meta))
            .map { mapper.parse(it) }
    }

    override fun sincronizar(): Completable {
        return Completable.merge(listOf(
            sincronizarNuevos(),
            sincronizarEditados(),
            sincronizarEliminados()))
    }
}
