package biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.profile.preparingiskey.co

import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.masvendido.productosmasvendidos.EsikaLbelData
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.masvendido.productosmasvendidos.TopSoldProduct
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.salesconsultant.BrandsAndCategories
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.salesconsultant.MultiMarkCategories

interface TopSalesCoRepository {

    suspend fun getBrandsAndCategories(
        consultantCode: String,
        campaign: List<String>
    ): BrandsAndCategories

    suspend fun getMostSoldProducts(
        consultantCode: String,
        campaign: List<String>
    ): List<TopSoldProduct>

    suspend fun getMultiCategory(
        consultantCode: String,
        campaign: List<String>
    ): MultiMarkCategories

    suspend fun getProductsLbelEsika(
        consultantCode: String,
        campaign: List<String>
    ): EsikaLbelData

}
