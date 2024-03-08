package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.performance

import biz.belcorp.salesforce.core.data.repository.businesspartners.data.BusinessPartnerDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.performance.mapper.PerformanceMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.profile.ProfilePerformanceSe
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.perfil.PerformanceSeRepository

class PerformanceSeDataRepository(
    private val dataStore: BusinessPartnerDataStore,
    private val mapper: PerformanceMapper
) : PerformanceSeRepository {

    override fun getPerformance(personCode: String): ProfilePerformanceSe {
        val businessPartner = dataStore.getBusinessPartner(personCode)
        return mapper.map(requireNotNull(businessPartner))
    }
}
