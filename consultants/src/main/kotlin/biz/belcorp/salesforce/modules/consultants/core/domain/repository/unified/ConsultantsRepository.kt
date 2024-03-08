package biz.belcorp.salesforce.modules.consultants.core.domain.repository.unified

import biz.belcorp.salesforce.core.domain.entities.consultants.Consultant
import biz.belcorp.salesforce.modules.consultants.core.domain.entities.unified.Filter

interface ConsultantsRepository {

    suspend fun getConsultants(filter: Filter): List<Consultant>

}
