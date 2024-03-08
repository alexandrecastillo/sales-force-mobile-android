package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.profile.ProfilePerformanceSe

interface PerformanceSeRepository {

    fun getPerformance(personCode: String): ProfilePerformanceSe

}
