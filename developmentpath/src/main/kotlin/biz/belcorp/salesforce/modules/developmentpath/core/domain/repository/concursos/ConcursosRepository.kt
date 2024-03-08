package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.concursos

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.concursos.Concurso
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import io.reactivex.Single

interface ConcursosRepository {
    fun buscarConcurso(persona: PersonaRdd): Single<Concurso>
    suspend fun sync(person: PersonaRdd, country: String)
}
