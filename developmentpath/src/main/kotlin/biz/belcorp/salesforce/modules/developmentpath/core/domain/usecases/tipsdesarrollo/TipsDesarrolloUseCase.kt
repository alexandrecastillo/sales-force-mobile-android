package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.tipsdesarrollo

import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.core.domain.executor.PostExecutionThread
import biz.belcorp.salesforce.core.domain.executor.ThreadExecutor
import biz.belcorp.salesforce.core.domain.usecases.base.UseCase
import biz.belcorp.salesforce.core.features.handlers.observers.BaseSingleObserver
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.personas.PersonaRdd
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.tipsdesarrollo.TipDesarrollo
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.personas.RddPersonaRepository
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.tipsdesarrollo.TipsDesarrolloRepository

class TipsDesarrolloUseCase(
    private val tipsDesarrolloRepository: TipsDesarrolloRepository,
    private val personaRepository: RddPersonaRepository
) {

    fun obtener(identifier: PersonIdentifier): TipDesarrollo {
        val person = personaRepository.recuperarPersonaPorId(identifier.id, identifier.role)
        return tipsDesarrolloRepository.obtenerTipsDesarrollo(requireNotNull(person))
    }

}
