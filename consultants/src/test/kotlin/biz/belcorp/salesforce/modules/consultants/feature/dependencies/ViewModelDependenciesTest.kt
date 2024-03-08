package biz.belcorp.salesforce.modules.consultants.feature.dependencies

import biz.belcorp.salesforce.core.utils.get
import biz.belcorp.salesforce.modules.consultants.base.BaseDependenciesTest
import biz.belcorp.salesforce.modules.consultants.features.filters.baseunified.view.SearchConsultantViewModel
import biz.belcorp.salesforce.modules.consultants.features.quantity.ConsultantsQuantityViewModel
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test


class ViewModelDependenciesTest : BaseDependenciesTest() {

    @Test
    fun `resolver dependencies for ConsultantQuantityViewModel`() {
        get<ConsultantsQuantityViewModel>().shouldNotBeNull()
    }

    @Test
    fun `resolver dependencies for ConsultantsViewModel`() {
        get<SearchConsultantViewModel>().shouldNotBeNull()
    }

}
