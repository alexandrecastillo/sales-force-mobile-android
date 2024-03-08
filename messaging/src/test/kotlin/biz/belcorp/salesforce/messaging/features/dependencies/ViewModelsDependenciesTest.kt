package biz.belcorp.salesforce.messaging.features.dependencies

import biz.belcorp.salesforce.messaging.base.BaseDependenciesTest
import biz.belcorp.salesforce.messaging.features.news.NewsViewModel
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test
import org.koin.core.get

class ViewModelsDependenciesTest : BaseDependenciesTest() {

    @Test
    fun `solving test for NewsViewModel`() {
        get<NewsViewModel>().shouldNotBeNull()
    }

}
