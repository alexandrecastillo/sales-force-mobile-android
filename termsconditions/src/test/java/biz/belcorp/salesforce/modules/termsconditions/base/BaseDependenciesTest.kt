package biz.belcorp.salesforce.modules.termsconditions.base

import biz.belcorp.salesforce.modules.termsconditions.di.injectDependenciesTestModules
import org.junit.Before
import org.koin.test.AutoCloseKoinTest

abstract class BaseDependenciesTest : AutoCloseKoinTest() {

    @Before
    fun setup() {
        injectDependenciesTestModules()
    }

}
