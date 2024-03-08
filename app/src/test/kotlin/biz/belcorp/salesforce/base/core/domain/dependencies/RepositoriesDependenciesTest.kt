package biz.belcorp.salesforce.base.core.domain.dependencies

import biz.belcorp.salesforce.base.base.BaseDependenciesTest
import biz.belcorp.salesforce.base.core.domain.repository.options.OptionsRepository
import biz.belcorp.salesforce.core.domain.repository.configuration.ConfigurationRepository
import biz.belcorp.salesforce.core.domain.repository.ua.UaInfoRepository
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test
import org.koin.core.get

class RepositoriesDependenciesTest : BaseDependenciesTest() {

    @Test
    fun `resolviendo dependencias para OptionsRepository`() {
        get<OptionsRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolve dependencies for UaRepository`() {
        get<UaInfoRepository>().shouldNotBeNull()
    }

    @Test
    fun `resolve denpendencies for ConfigurationRepository`() {
        get<ConfigurationRepository>().shouldNotBeNull()
    }

}
