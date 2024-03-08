package biz.belcorp.salesforce.modules.digital.base

import biz.belcorp.salesforce.modules.digital.di.injectDependenciesTestModules
import org.junit.Before
import org.koin.test.AutoCloseKoinTest

abstract class BaseDependenciesTest : AutoCloseKoinTest() {

    @Before
    fun setup() {
        injectDependenciesTestModules()
    }

}
