package biz.belcorp.salesforce.modules.developmentmaterial.base

import biz.belcorp.salesforce.modules.developmentmaterial.di.injectDependenciesTestModules
import org.junit.Before
import org.koin.test.AutoCloseKoinTest


abstract class BaseDependenciesTest : AutoCloseKoinTest() {

    @Before
    fun setup() {
        injectDependenciesTestModules()
    }

}
