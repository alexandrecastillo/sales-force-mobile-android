package biz.belcorp.salesforce.modules.brightpath.core.domain.usecases.consultants

import biz.belcorp.salesforce.core.constants.Constant.PERCENTAGE
import biz.belcorp.salesforce.modules.brightpath.core.domain.repository.ConsultantsRepository


class ConsultantsUseCase(
    private val repository: ConsultantsRepository
) {

    suspend fun getConsultantsMayChangeLevelListSize(): Int {
        val section = PERCENTAGE.toString()
        return repository.getConsultantsMayChangeLevelListSize(section)
    }
}
