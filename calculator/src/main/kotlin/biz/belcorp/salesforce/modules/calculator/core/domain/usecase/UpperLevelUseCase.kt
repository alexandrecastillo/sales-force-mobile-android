package biz.belcorp.salesforce.modules.calculator.core.domain.usecase

import biz.belcorp.salesforce.modules.calculator.core.domain.entities.UpperLevel
import biz.belcorp.salesforce.modules.calculator.core.domain.repository.upperlevel.UpperLevelRepository

class UpperLevelUseCase (
    private val repository: UpperLevelRepository
) {

    suspend fun all(): List<UpperLevel>? {
        val list = repository.list()
        return list
    }
}
