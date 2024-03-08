package biz.belcorp.salesforce.modules.developmentmaterial.core.domain.dependencies

import biz.belcorp.salesforce.modules.developmentmaterial.base.BaseDependenciesTest
import biz.belcorp.salesforce.modules.developmentmaterial.core.domain.repository.MaterialesDesarrolloRepository
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test
import org.koin.core.get


class RepositoriesDependenciesTest : BaseDependenciesTest() {

    @Test
    fun `resolviendo dependencias para MaterialesDesarrolloRepository`() {
        get<MaterialesDesarrolloRepository>().shouldNotBeNull()
    }

}
