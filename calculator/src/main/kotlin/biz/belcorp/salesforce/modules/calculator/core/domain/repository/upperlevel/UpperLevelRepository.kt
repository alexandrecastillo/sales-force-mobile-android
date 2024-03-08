package biz.belcorp.salesforce.modules.calculator.core.domain.repository.upperlevel

import biz.belcorp.salesforce.modules.calculator.core.domain.entities.UpperLevel

interface UpperLevelRepository {
    suspend fun list(): List<UpperLevel>?
}
