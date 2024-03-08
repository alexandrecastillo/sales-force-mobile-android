package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.prepararseesclave.detail.masvendido.marcasycategorias

import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.datasource.profile.prepararseesclave.salesconsultant.TopSalesCoDataStore
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.profile.prepararseesclave.detail.masvendido.marcascategorias.BrandsAndCategoriesMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.profile.prepararseesclave.detail.masvendido.marcascategorias.MultiMarkCategoriesMapper
import biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.profile.prepararseesclave.detail.masvendido.productosmasvendidos.TopProductsMapper
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.masvendido.productosmasvendidos.EsikaLbelData
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.masvendido.productosmasvendidos.TopSoldProduct
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.salesconsultant.BrandsAndCategories
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.salesconsultant.MultiMarkCategories
import biz.belcorp.salesforce.modules.developmentpath.core.domain.repository.profile.preparingiskey.co.TopSalesCoRepository

class TopSalesCoDataRepository(
    private val salesConsultantDataStore: TopSalesCoDataStore,
    private val marksAndCategoriesMapper: BrandsAndCategoriesMapper,
    private val topProductsMapper: TopProductsMapper,
    private val multiMarkCategoriesMapper: MultiMarkCategoriesMapper
) : TopSalesCoRepository {

    override suspend fun getBrandsAndCategories(
        consultantCode: String,
        campaign: List<String>
    ): BrandsAndCategories {
        val entities = getSalesConsultantData(consultantCode, campaign)
        return marksAndCategoriesMapper.map(entities)
    }

    override suspend fun getMostSoldProducts(
        consultantCode: String,
        campaign: List<String>
    ): List<TopSoldProduct> {
        val entities = getSalesConsultantData(consultantCode, campaign)
        return topProductsMapper.mapCo(entities)
    }

    override suspend fun getMultiCategory(
        consultantCode: String,
        campaign: List<String>
    ): MultiMarkCategories {
        val entities = getSalesConsultantData(consultantCode, campaign)
        return multiMarkCategoriesMapper.map(entities)
    }

    override suspend fun getProductsLbelEsika(consultantCode: String, campaign: List<String>): EsikaLbelData {
        val entities = getSalesConsultantData(consultantCode, campaign)
        return topProductsMapper.mapEsikaLbel(entities)
    }

    private fun getSalesConsultantData(
        consultantCode: String,
        campaigns: List<String>
    ): List<TopSalesCoEntity> {
        return salesConsultantDataStore.getSalesConsultant(consultantCode, campaigns)
    }


}


