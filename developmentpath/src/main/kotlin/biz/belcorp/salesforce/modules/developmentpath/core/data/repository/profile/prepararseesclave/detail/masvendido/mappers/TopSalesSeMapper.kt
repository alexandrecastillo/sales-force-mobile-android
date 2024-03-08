package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.prepararseesclave.detail.masvendido.mappers

import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoBrandsEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoTopProductEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesConsultantTopProductHistoryEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salessection.TopSalesSeEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.common.Brand
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.common.Product
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.se.TopSalesSeDto

class TopSalesSeMapper {

    fun map(items: List<TopSalesSeDto.TopSaleSe>) = items.map { map(it) }

    private fun map(entity: TopSalesSeDto.TopSaleSe): TopSalesSeEntity =
        with(entity) {
            val topSalesSeEntity = TopSalesSeEntity(
                campaign = campaign,
                region = region,
                zone = zone,
                section = section
            )

            val brands = brands.map { mapToBrandEntity(it) }
            val products = topProducts.map { mapToTopProductEntity(it) }

            topSalesSeEntity.brands.addAll(brands)
            topSalesSeEntity.topProducts.addAll(products)

            return topSalesSeEntity
        }

    private fun mapToTopProductEntity(product: Product): TopSalesCoTopProductEntity =
        with(product) {
            val entity = TopSalesCoTopProductEntity(
                top = top,
                name = name,
                units = units
            )

            val histories = histories.map { mapToHistoryEntity(it) }

            entity.topProductsHistory.addAll(histories)

            return entity
        }

    private fun mapToHistoryEntity(history: Product.Historical) = with(history) {
        return@with TopSalesConsultantTopProductHistoryEntity(
            campaign = campaign,
            units = units
        )
    }

    private fun mapToBrandEntity(brand: Brand) =
        with(brand) {
            return@with TopSalesCoBrandsEntity(
                name = name,
                units = units
            )
        }

}
