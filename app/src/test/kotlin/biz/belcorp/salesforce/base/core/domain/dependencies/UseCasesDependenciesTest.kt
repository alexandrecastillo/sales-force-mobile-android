package biz.belcorp.salesforce.base.core.domain.dependencies

import biz.belcorp.salesforce.base.base.BaseDependenciesTest
import biz.belcorp.salesforce.base.core.domain.usecases.options.GetMenuOptionsUseCase
import biz.belcorp.salesforce.base.core.domain.usecases.options.GetShortcutOptionsUseCase
import biz.belcorp.salesforce.core.domain.usecases.configuration.ConfigurationUseCase
import biz.belcorp.salesforce.core.domain.usecases.ua.UaInfoUseCase
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test
import org.koin.core.get


class UseCasesDependenciesTest : BaseDependenciesTest() {

    @Test
    fun `resolviendo dependencias para GetMenuOptionsUseCase`() {
        get<GetMenuOptionsUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolviendo dependencias para GetShortcutOptionsUseCase`() {
        get<GetShortcutOptionsUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolve dependencies for UaUseCase`() {
        get<UaInfoUseCase>().shouldNotBeNull()
    }

    @Test
    fun `resolve dependencies for ConfigurationUseCase`() {
        get<ConfigurationUseCase>().shouldNotBeNull()
    }
}
