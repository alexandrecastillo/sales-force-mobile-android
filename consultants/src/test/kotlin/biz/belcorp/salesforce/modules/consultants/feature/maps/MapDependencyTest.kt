package biz.belcorp.salesforce.modules.consultants.feature.maps

import biz.belcorp.salesforce.core.utils.get
import biz.belcorp.salesforce.modules.consultants.base.BaseDependenciesTest
import biz.belcorp.salesforce.modules.consultants.features.maps.MapViewModel
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test

class MapDependencyTest : BaseDependenciesTest() {

    @Test
    fun `solving dependencies for MapViewModel()`() {
        get<MapViewModel>().shouldNotBeNull()
    }

}
