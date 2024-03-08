package biz.belcorp.salesforce.modules.calculator.core.data.repository.partnervariable

import biz.belcorp.salesforce.modules.calculator.core.data.repository.partnervariable.data.PartnerVariableDBDataStore
import biz.belcorp.salesforce.modules.calculator.core.data.repository.partnervariable.mapper.PartnerVariableEntityDataMapper
import biz.belcorp.salesforce.modules.calculator.core.domain.entities.PartnerVariable
import biz.belcorp.salesforce.modules.calculator.core.domain.repository.partnervariable.PartnerVariableRepository
import io.reactivex.Single

class PartnerVariableDataRepository (
    private val partnerVariableDBDataStore: PartnerVariableDBDataStore,
    private val partnerVariableEntityDataMapper: PartnerVariableEntityDataMapper) : PartnerVariableRepository {

    override suspend fun getPartnerVariable(): PartnerVariable {
        return partnerVariableEntityDataMapper.parsePartnerVariable(partnerVariableDBDataStore.getPartnerVariable())
    }
}
