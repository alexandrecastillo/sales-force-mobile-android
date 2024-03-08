package biz.belcorp.salesforce.modules.creditinquiry.base

import biz.belcorp.salesforce.modules.creditinquiry.di.injectDependenciesTestModules
import org.junit.Before
import org.koin.test.AutoCloseKoinTest


abstract class BaseDependenciesTest : AutoCloseKoinTest() {

    @Before
    fun setup() {
        injectDependenciesTestModules()
    }

}
