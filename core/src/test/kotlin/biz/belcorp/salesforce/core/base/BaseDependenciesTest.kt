package biz.belcorp.salesforce.core.base

import biz.belcorp.salesforce.core.di.injectDependenciesTestModules
import org.junit.Before
import org.koin.test.AutoCloseKoinTest


abstract class BaseDependenciesTest : AutoCloseKoinTest() {

    @Before
    fun setup() {
        injectDependenciesTestModules()
    }

}
