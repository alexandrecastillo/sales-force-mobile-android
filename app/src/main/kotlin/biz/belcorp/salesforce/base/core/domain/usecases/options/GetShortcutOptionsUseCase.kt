package biz.belcorp.salesforce.base.core.domain.usecases.options

import biz.belcorp.salesforce.base.core.domain.repository.options.OptionsRepository
import biz.belcorp.salesforce.core.domain.entities.options.Option
import biz.belcorp.salesforce.core.domain.usecases.configuration.ConfigurationUseCase

class GetShortcutOptionsUseCase(
    private val optionsRepository: OptionsRepository,
    private val configurationUseCase: ConfigurationUseCase
) {

    suspend fun getOptions(): List<Option> {
        return optionsRepository.getShortcutOptions().filter { filterBrightPath(it) }
    }

    private suspend fun filterBrightPath(value: Option) = when (value.code) {
        OptionsIdentifier.BRIGHT_PATH.toLong() -> isPdv()
        else -> true
    }

    private suspend fun isPdv(): Boolean = configurationUseCase.getConfiguration().isPdv
}
