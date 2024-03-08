package biz.belcorp.salesforce.base.core.domain.usecases.options

import biz.belcorp.salesforce.base.core.domain.repository.options.OptionsRepository
import biz.belcorp.salesforce.core.domain.entities.options.Option


class GetMenuOptionsUseCase(
    private val optionsRepository: OptionsRepository
) {

    suspend fun getOptions(): List<Option> {
        return optionsRepository.getMenuOptions()
    }

}
