package biz.belcorp.salesforce.modules.calculator.base

import biz.belcorp.salesforce.modules.calculator.di.injectDependenciesTestModules
import org.junit.Before
import org.koin.test.AutoCloseKoinTest

abstract class BaseDependenciesTest : AutoCloseKoinTest() {

    @Before
    fun setup() {
        injectDependenciesTestModules()
    }

}
