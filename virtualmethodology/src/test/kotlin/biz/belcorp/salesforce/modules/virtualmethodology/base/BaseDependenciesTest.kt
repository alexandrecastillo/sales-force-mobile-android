package biz.belcorp.salesforce.modules.virtualmethodology.base


import biz.belcorp.salesforce.modules.virtualmethodology.di.injectDependenciesTestModules
import org.junit.Before
import org.koin.test.AutoCloseKoinTest


abstract class BaseDependenciesTest : AutoCloseKoinTest() {

    @Before
    fun setup() {
        injectDependenciesTestModules()
    }

}
