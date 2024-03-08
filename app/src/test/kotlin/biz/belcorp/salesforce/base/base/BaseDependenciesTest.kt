package biz.belcorp.salesforce.base.base

import biz.belcorp.salesforce.base.di.injectDependenciesTestModules
import org.junit.Before
import org.koin.test.AutoCloseKoinTest


abstract class BaseDependenciesTest : AutoCloseKoinTest() {

    @Before
    fun setup() {
        injectDependenciesTestModules()
    }

}
