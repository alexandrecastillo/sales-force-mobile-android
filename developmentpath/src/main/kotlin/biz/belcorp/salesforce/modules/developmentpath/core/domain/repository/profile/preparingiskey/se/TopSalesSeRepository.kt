package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.profile.preparingiskey.se

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.masvendido.productosmasvendidos.TopSoldProduct
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.salesconsultant.BrandContainer

interface TopSalesSeRepository {
    suspend fun getBrands(uaKey: LlaveUA, campaigns: List<String>): BrandContainer
    suspend fun getMostSoldProducts(uaKey: LlaveUA, campaigns: List<String>): List<TopSoldProduct>
}
