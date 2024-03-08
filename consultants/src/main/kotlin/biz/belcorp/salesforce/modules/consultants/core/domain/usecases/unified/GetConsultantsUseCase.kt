package biz.belcorp.salesforce.modules.consultants.core.domain.usecases.unified

import biz.belcorp.salesforce.core.domain.entities.consultants.Consultant
import biz.belcorp.salesforce.modules.consultants.core.domain.entities.unified.Filter
import biz.belcorp.salesforce.modules.consultants.core.domain.repository.unified.ConsultantsRepository

class GetConsultantsUseCase(
    private val consultantsRepository: ConsultantsRepository
) {

    suspend fun getConsultants(filter: Filter): List<Consultant> {
        return consultantsRepository.getConsultants(filter)
    }

    suspend fun getConsultantsQuantity(filter: Filter): Int {
        return getConsultants(filter).size
    }

}
