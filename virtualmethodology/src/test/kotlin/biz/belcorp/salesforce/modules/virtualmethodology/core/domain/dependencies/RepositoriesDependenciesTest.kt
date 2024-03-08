package biz.belcorp.salesforce.modules.virtualmethodology.core.domain.dependencies

import biz.belcorp.salesforce.modules.virtualmethodology.base.BaseDependenciesTest
import biz.belcorp.salesforce.modules.virtualmethodology.core.domain.repository.GroupSegRepository
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test
import org.koin.core.get


class RepositoriesDependenciesTest : BaseDependenciesTest() {

    @Test
    fun `resolviendo dependencias para GroupSegRepository`() {
        get<GroupSegRepository>().shouldNotBeNull()
    }

}
