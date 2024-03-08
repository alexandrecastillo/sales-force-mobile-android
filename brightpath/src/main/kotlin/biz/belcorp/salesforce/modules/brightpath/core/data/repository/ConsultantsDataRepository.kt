package biz.belcorp.salesforce.modules.brightpath.core.data.repository

import biz.belcorp.salesforce.modules.brightpath.core.data.repository.data.consultants.ConsultantsDataStore
import biz.belcorp.salesforce.modules.brightpath.core.domain.repository.ConsultantsRepository

class ConsultantsDataRepository(private val dataStore: ConsultantsDataStore): ConsultantsRepository {

    override suspend fun getConsultantsMayChangeLevelListSize(section: String): Int {
        return dataStore.getConsultantsMayChangeLevelListSize(section)
    }
}
