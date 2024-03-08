package biz.belcorp.salesforce.modules.developmentpath.core.data.dependencies

import biz.belcorp.salesforce.core.utils.get
import biz.belcorp.salesforce.modules.developmentpath.base.BaseDependenciesTest
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.graficos.data.ProfileSeOrdersU6CDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.ventas.cloud.SalesConsultantCloudStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.ventas.data.SaleConsultantDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.graficos.pedidosu6c.cloud.ProfileSeOrdersU6CCloudStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.cloud.TopSalesCoCloudStore
import org.amshove.kluent.shouldNotBeNull
import org.junit.Test

class DataSourcesDependenciesTest : BaseDependenciesTest() {

    @Test
    fun `solving dependencies for SalesConsultantCloudStore`() {
        get<SalesConsultantCloudStore>().shouldNotBeNull()
    }

    @Test
    fun `solving dependencies for TopSalesConsultantCloudStore`() {
        get<TopSalesCoCloudStore>().shouldNotBeNull()
    }

    @Test
    fun `solving dependencies for SaleConsultantDataStore`() {
        get<SaleConsultantDataStore>().shouldNotBeNull()
    }

    @Test
    fun `solving dependencies for ProfileSeOrdersU6CCloudStore`() {
        get<ProfileSeOrdersU6CCloudStore>().shouldNotBeNull()
    }

    @Test
    fun `solving dependencies for ProfileSeOrdersU6CDataStore`() {
        get<ProfileSeOrdersU6CDataStore>().shouldNotBeNull()
    }

}
