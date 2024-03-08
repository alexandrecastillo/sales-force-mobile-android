package biz.belcorp.salesforce.modules.calculator.core.domain.repository.partnervariable

import biz.belcorp.salesforce.modules.calculator.core.domain.entities.PartnerVariable
import io.reactivex.Single

interface PartnerVariableRepository {
    suspend fun getPartnerVariable(): PartnerVariable
}
