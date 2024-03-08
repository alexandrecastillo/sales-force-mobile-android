package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.metas

import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.metas.cloud.MetaConsultoraCloudDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.metas.data.MetaConsultoraDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.metas.MetaConsultoraMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.metas.MetaConsultora
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil.MetaConsultoraRepository
import io.reactivex.Completable
import io.reactivex.Single

class MetaConsultoraDataRepository(private val cloudStore: MetaConsultoraCloudDataStore,
                                   private val dbStore: MetaConsultoraDBDataStore,
                                   private val metaConsultoraMapper: MetaConsultoraMapper
)
    : MetaConsultoraRepository {

    override fun guardar(metaConsultora: MetaConsultora): Completable {
        val metaEntity = metaConsultoraMapper.parse(metaConsultora)
        val metasConsultora = listOf(metaEntity)
        return dbStore.guardar(metaEntity)
            .flatMap { cloudStore.guardar(metasConsultora) }
            .flatMapCompletable { dbStore.marcarComoEnviado(metaEntity) }
            .onErrorResumeNext { Completable.complete() }
    }

    override fun obtenerMeta(consultoraId: Int): Single<List<MetaConsultora>> {
        return dbStore.obtener(consultoraId)
            .map { metaConsultoraMapper.reverseParse(it) }
    }

    override fun sincronizar(): Completable {
        return dbStore.obtenerNoEnviadas()
            .filter { it.isNotEmpty() }
            .flatMap { cloudStore.guardar(it).toMaybe() }
            .flatMapCompletable { dbStore.marcarComoEnviadas() }
    }
}
