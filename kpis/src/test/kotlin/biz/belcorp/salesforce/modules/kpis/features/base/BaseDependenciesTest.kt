package biz.belcorp.salesforce.modules.kpis.features.base

import biz.belcorp.salesforce.modules.kpis.features.di.injectDependenciesTestModules
import org.junit.Before
import org.koin.test.AutoCloseKoinTest

abstract class BaseDependenciesTest : AutoCloseKoinTest() {

    @Before
    fun setup() {
        injectDependenciesTestModules()
    }

}
