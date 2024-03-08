package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.concursos

import biz.belcorp.salesforce.core.entities.sql.concursos.ConcursosEntity
import biz.belcorp.salesforce.core.utils.doOnSingle
import biz.belcorp.salesforce.core.utils.isCO
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.concursos.cloud.ConcursosDBCloudStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.concursos.data.ConcursosDBDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.concursos.ConcursosEntityDataMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.concursos.Concurso
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.concursos.ConcursosRepository
import io.reactivex.Single

class ConcursosDataRepository(
    val concursosDBDataStore: ConcursosDBDataStore,
    val concursosDBCloudStore: ConcursosDBCloudStore,
    val concursosMapper: ConcursosEntityDataMapper
) : ConcursosRepository {


    override fun buscarConcurso(persona: PersonaRdd): Single<Concurso> {
        return doOnSingle {
            val concurso = concursosDBDataStore.obtenerConcurso(persona)
            concursosMapper.parse(concurso ?: ConcursosEntity())
        }
    }

    override suspend fun sync(person: PersonaRdd, country: String) {
        if (!person.rol.isCO()) return
        val response = concursosDBCloudStore.sync(person, country)
        val entities = concursosMapper.parse(person, response.resultado)
        concursosDBDataStore.guardar(entities)
    }
}
