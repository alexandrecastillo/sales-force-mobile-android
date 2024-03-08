package biz.belcorp.salesforce.modules.developmentpath.core.domain.usecases.profile

import biz.belcorp.salesforce.core.domain.entities.people.PersonIdentifier
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.profile.ProfilePerformanceSe
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil.PerformanceSeRepository

class GetPerformanceSeUseCase(
    private val performanceSeRepository: PerformanceSeRepository
) {

    fun getPerformance(personIdentifier: PersonIdentifier): ProfilePerformanceSe {
        return performanceSeRepository.getPerformance(personIdentifier.code)
    }

}
