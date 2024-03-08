package biz.belcorp.salesforce.modules.billing.base

import biz.belcorp.salesforce.modules.billing.di.injectDependenciesTestModules
import org.junit.Before
import org.koin.test.AutoCloseKoinTest

abstract class BaseDependenciesTest : AutoCloseKoinTest() {

    @Before
    fun setup() {
        injectDependenciesTestModules()
    }
}
