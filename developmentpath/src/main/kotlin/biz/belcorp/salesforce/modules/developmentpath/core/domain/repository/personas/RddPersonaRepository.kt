package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas

import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.ModeloCreacionVisita
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.visitas.Visita
import io.reactivex.Single

interface RddPersonaRepository {
    fun recuperarPersonaPorId(personaId: Long, rol: Rol): PersonaRdd?
    fun singleRecuperarPersonaPorId(identificador: PersonaRdd.Identificador): Single<PersonaRdd>
    fun recuperarPersona(identificador: PersonaRdd.Identificador): PersonaRdd?
    fun actualizarVisita(visita: Visita)
    fun crearVisita(visita: ModeloCreacionVisita)
    fun recuperarVisita(id: Long): Visita?
    fun recuperarIdentificadorDeDuenioDePlan(planId: Long): Pair<Long?, Rol>
}
