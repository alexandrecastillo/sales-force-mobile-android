package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.metas

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.Persona
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.sesion.persona
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.anotaciones.data.AnotacionDBStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.metas.cloud.MetaSociaCloudDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.metas.MetaSociaMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.metas.MetaSocia
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.metas.MetasSociaRepository
import io.reactivex.Completable
import io.reactivex.Single

class MetasSociaDataRepository(
    cloudStore: MetaSociaCloudDataStore,
    private val dbStore: AnotacionDBStore,
    private val mapper: MetaSociaMapper
) : MetasDataRepository(cloudStore, dbStore), MetasSociaRepository {

    override fun guardar(meta: MetaSocia): Completable {
        val entidad = mapper.parse(meta)
        return dbStore.guardar(entidad)
            .doAfterSuccess {
                Completable.complete()
                sincronizarNuevos().subscribe({}, {})
            }
            .toCompletable()
    }

    override fun actualizar(meta: MetaSocia): Completable {
        val entidad = mapper.parse(meta)
        return dbStore.actualizar(entidad)
            .doAfterSuccess {
                Completable.complete()
                sincronizarEditados().subscribe({}, {})
            }
            .toCompletable()

    }

    override fun eliminar(meta: MetaSocia): Completable {
        val entidad = mapper.parse(meta)
        return dbStore.eliminar(entidad)
            .doOnComplete {
                Completable.complete()
                sincronizarEliminados().subscribe({}, {})
            }
    }

    override fun listar(persona: PersonaRdd): Single<List<MetaSocia>> {
        return dbStore.listarMetas(persona.id).map { mapper.parse(it) }
    }
}
