package biz.belcorp.salesforce.core.domain.usecases.configuration

import biz.belcorp.salesforce.core.domain.entities.configuration.Configuration
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.domain.repository.configuration.ConfigurationRepository
import biz.belcorp.salesforce.core.domain.repository.session.SessionRepository

class ConfigurationUseCase(
    private val configurationRepository: ConfigurationRepository,
    private val sessionRepository: SessionRepository
) {
    val session get() = requireNotNull(sessionRepository.getSession())

    suspend fun getConfiguration(uaKey: LlaveUA): Configuration {
        return configurationRepository.getConfiguration(uaKey)
    }

    suspend fun getConfiguration(): Configuration {
        return configurationRepository.getConfiguration(session.llaveUA)
    }

}
