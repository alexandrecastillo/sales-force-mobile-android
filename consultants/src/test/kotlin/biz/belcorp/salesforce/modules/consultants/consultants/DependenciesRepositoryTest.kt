package biz.belcorp.salesforce.modules.consultants.consultants

import biz.belcorp.salesforce.modules.consultants.base.BaseDependenciesTest
import biz.belcorp.salesforce.core.domain.repository.consultant.ConsultantsSyncRepository
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test
import org.koin.core.get

class DependenciesRepositoryTest : BaseDependenciesTest() {

    @Test
    fun `resolviendo dependencias para ConsultantRepository`() {
        get<ConsultantsSyncRepository>().shouldNotBeNull()
    }

}
