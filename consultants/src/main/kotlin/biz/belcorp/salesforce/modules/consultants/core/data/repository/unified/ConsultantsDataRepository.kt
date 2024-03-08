package biz.belcorp.salesforce.modules.consultants.core.data.repository.unified

import biz.belcorp.salesforce.core.data.repository.configuration.data.ConfigurationDataStore
import biz.belcorp.salesforce.core.domain.entities.consultants.Consultant
import biz.belcorp.salesforce.modules.consultants.core.data.repository.consultants.mapper.ConsultantsMapper
import biz.belcorp.salesforce.modules.consultants.core.data.repository.unified.data.ConsultantsDataStore
import biz.belcorp.salesforce.modules.consultants.core.domain.entities.unified.Filter
import biz.belcorp.salesforce.modules.consultants.core.domain.repository.unified.ConsultantsRepository

class ConsultantsDataRepository(
    private val consultantsDataStore: ConsultantsDataStore,
    private val configDataStore: ConfigurationDataStore,
    private val consultantsMapper: ConsultantsMapper
) : ConsultantsRepository {

    private val config by lazy { requireNotNull(configDataStore.getConfigurationCountry()) }

    override suspend fun getConsultants(filter: Filter): List<Consultant> {
        val consultants = consultantsDataStore.getConsultants(filter)
        return consultantsMapper.map(consultants, config.phoneCode)
    }

}
