package biz.belcorp.salesforce.modules.calculator.core.domain.usecase

import biz.belcorp.salesforce.modules.calculator.core.domain.entities.PartnerVariable
import biz.belcorp.salesforce.modules.calculator.core.domain.repository.partnervariable.PartnerVariableRepository

class PartnerVariableUseCase (
    private val repository: PartnerVariableRepository
) {
    suspend fun getPartnerVariable() : PartnerVariable = repository.getPartnerVariable()
}
