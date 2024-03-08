package biz.belcorp.salesforce.modules.postulants.base

import biz.belcorp.salesforce.modules.postulants.di.injectDependenciesTestModules
import org.junit.Before
import org.koin.test.AutoCloseKoinTest

abstract class BaseDependenciesTest : AutoCloseKoinTest() {

    @Before
    fun setup() {
        injectDependenciesTestModules()
    }
}
