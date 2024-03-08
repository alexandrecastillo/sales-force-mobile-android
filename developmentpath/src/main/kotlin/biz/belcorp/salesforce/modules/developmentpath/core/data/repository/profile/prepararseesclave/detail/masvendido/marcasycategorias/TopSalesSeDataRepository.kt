package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.prepararseesclave.detail.masvendido.marcasycategorias

import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.salesconsultant.TopSalesSeDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.profile.prepararseesclave.detail.masvendido.marcascategorias.BrandsMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.profile.prepararseesclave.detail.masvendido.productosmasvendidos.TopProductsMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.masvendido.productosmasvendidos.TopSoldProduct
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.salesconsultant.BrandContainer
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.profile.preparingiskey.se.TopSalesSeRepository

class TopSalesSeDataRepository(
    private val salesConsultantDataStore: TopSalesSeDataStore,
    private val marksAndCategoriesMapper: BrandsMapper,
    private val topProductsMapper: TopProductsMapper
) : TopSalesSeRepository {

    override suspend fun getBrands(
        uaKey: LlaveUA,
        campaigns: List<String>
    ): BrandContainer {
        val entities = getSalesSeData(uaKey, campaigns)
        return marksAndCategoriesMapper.map(entities)
    }

    override suspend fun getMostSoldProducts(
        uaKey: LlaveUA,
        campaigns: List<String>
    ): List<TopSoldProduct> {
        val entities = getSalesSeData(uaKey, campaigns)
        return topProductsMapper.mapSe(entities)
    }

    private fun getSalesSeData(uaKey: LlaveUA, campaigns: List<String>) =
        salesConsultantDataStore.getTopSalesSe(uaKey, campaigns)
}


