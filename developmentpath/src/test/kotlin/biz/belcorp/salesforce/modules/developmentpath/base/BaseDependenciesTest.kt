package biz.belcorp.salesforce.modules.developmentpath.base

import biz.belcorp.salesforce.modules.developmentpath.di.injectDependenciesTestModules
import org.junit.Before
import org.koin.test.AutoCloseKoinTest

abstract class BaseDependenciesTest : AutoCloseKoinTest() {

    @Before
    fun setup() {
        injectDependenciesTestModules()
    }
}
