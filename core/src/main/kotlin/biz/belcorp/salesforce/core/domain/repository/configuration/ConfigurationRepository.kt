package biz.belcorp.salesforce.core.domain.repository.configuration

import biz.belcorp.salesforce.core.domain.entities.configuration.Configuration
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA

interface ConfigurationRepository {

    suspend fun sync(country: String)
    suspend fun getConfiguration(uaKey: LlaveUA? = null): Configuration

}
