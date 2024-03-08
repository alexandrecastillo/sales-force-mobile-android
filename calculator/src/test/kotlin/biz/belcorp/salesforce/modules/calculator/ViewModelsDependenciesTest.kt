package biz.belcorp.salesforce.modules.calculator

import biz.belcorp.salesforce.core.utils.get
import biz.belcorp.salesforce.modules.calculator.base.BaseDependenciesTest
import biz.belcorp.salesforce.modules.calculator.feature.calculator.base.CalculatorViewModel
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test

class ViewModelsDependenciesTest : BaseDependenciesTest() {

    @Test
    fun `resolver dependencies for CalculatorDashboardViewModel`() {
        get<CalculatorViewModel>().shouldNotBeNull()
    }
}
