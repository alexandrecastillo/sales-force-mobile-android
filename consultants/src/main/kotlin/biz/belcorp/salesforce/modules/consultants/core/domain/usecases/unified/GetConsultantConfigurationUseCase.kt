package biz.belcorp.salesforce.modules.consultants.core.domain.usecases.unified

import biz.belcorp.salesforce.core.domain.entities.consultants.ConsultantConfiguration
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.usecases.configuration.ConfigurationUseCase

class GetConsultantConfigurationUseCase(
    private val configurationUseCase: ConfigurationUseCase
) {

    suspend fun getConsultantConfiguration(uaKey: LlaveUA): ConsultantConfiguration {
        val configuration = configurationUseCase.getConfiguration(uaKey)
        return ConsultantConfiguration(
            configuration.isPdv,
            configuration.currencySymbol,
            configuration.onlineStoreTitle,
            configuration.flagMto
        )
    }
}
