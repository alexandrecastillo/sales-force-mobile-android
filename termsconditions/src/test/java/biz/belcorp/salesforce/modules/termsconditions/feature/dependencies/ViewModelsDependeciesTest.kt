package biz.belcorp.salesforce.modules.termsconditions.feature.dependencies

import biz.belcorp.salesforce.core.utils.get
import biz.belcorp.salesforce.modules.termsconditions.base.BaseDependenciesTest
import biz.belcorp.salesforce.modules.termsconditions.features.dialog.TermsDialogViewModel
import biz.belcorp.salesforce.modules.termsconditions.features.termsconditions.TermsConditionsViewModel
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test

class ViewModelsDependeciesTest : BaseDependenciesTest() {

    @Test
    fun `resolver dependencies for TermsDialogViewModel`() {
        get<TermsDialogViewModel>().shouldNotBeNull()
    }

    @Test
    fun `resolver dependencies for TermsConditionsViewModel`() {
        get<TermsConditionsViewModel>().shouldNotBeNull()
    }

}
