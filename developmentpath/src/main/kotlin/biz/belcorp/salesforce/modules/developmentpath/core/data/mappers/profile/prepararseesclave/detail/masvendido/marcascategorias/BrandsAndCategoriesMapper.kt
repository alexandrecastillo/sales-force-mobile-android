package biz.belcorp.salesforce.modules.developmentpath.core.data.mappers.profile.prepararseesclave.detail.masvendido.marcascategorias

import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoBrandCategoriesCategoryEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoBrandCategoriesEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoCategoriesEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoCategoriesProductsBuyCampaignEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoCategoriesProductsEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoCategoriesProductsImageProductsEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.masvendido.marcasycategorias.BrandCategory
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.masvendido.marcasycategorias.BrandWithCategoryList
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.masvendido.marcasycategorias.BuyCampaigns
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.masvendido.marcasycategorias.Category
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.masvendido.marcasycategorias.ImagesProducts
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.masvendido.marcasycategorias.Product
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.salesconsultant.BrandsAndCategories

class BrandsAndCategoriesMapper {

    fun map(entities: List<TopSalesCoEntity>) = BrandsAndCategories(
        brands = getBrands(entities),
        categories = getCategories(entities),
        brandToPromote = if (entities.isNotEmpty()) {
            entities[0].brandToPromote
        } else Constant.EMPTY_STRING,
        topSellingBrand = if (entities.isNotEmpty()) {
            entities[0].topSellingBrand
        } else Constant.EMPTY_STRING
    )

    private fun getBrands(items: List<TopSalesCoEntity>) =
        items.firstOrNull()?.brandCategories?.toList()?.map { parseBrandCategories(it) }
            ?: emptyList()

    private fun getCategories(items: List<TopSalesCoEntity>) =
        items.firstOrNull()?.categories?.toList()?.map { parseCategory(it) } ?: emptyList()

    private fun parseBrandCategories(item: TopSalesCoBrandCategoriesEntity) = with(item) {
        val parsedBrandCategories = item.categories.map { parseBrandCategoriesCategoryList(it) }

        BrandWithCategoryList(
            brandType = BrandTypeBuilder.getType(item.brand),
            units = units,
            categoryList = parsedBrandCategories
        )
    }

    private fun parseBrandCategoriesCategoryList(topSalesCoBrandCategoriesCategoryEntity: TopSalesCoBrandCategoriesCategoryEntity): BrandCategory {
        return BrandCategory(
            order = topSalesCoBrandCategoriesCategoryEntity.order,
            name = topSalesCoBrandCategoriesCategoryEntity.name,
            units = topSalesCoBrandCategoriesCategoryEntity.units
        )
    }

    private fun parseCategory(item: TopSalesCoCategoriesEntity) = with(item) {
        Category(
            name = name,
            units = units,
            categoriesProducts = mapToCategoriesProduct(item.categoriesProducts)
        )
    }

    private fun mapToCategoriesProduct(items: List<TopSalesCoCategoriesProductsEntity>): List<Product> =
        items.map {
            Product(
                name = it.name, order = it.order, cuv = it.cuv, sku = it.sku,
                buyCampaigns = mapToBuyCampaigns(it.buyCampaignsProductParent),
                amount = it.amount,
                quantity = it.quantity,
                image = mapImageProduct(it.imagesProductParent)

            )
        }

    private fun mapToBuyCampaigns(
        buyCampaignsProductList:
        List<TopSalesCoCategoriesProductsBuyCampaignEntity>
    ): List<BuyCampaigns> =
        buyCampaignsProductList.map {
            BuyCampaigns(
                campaign1 = it.campaign1,
                campaign2 = it.campaign2,
                campaign3 = it.campaign3
            )
        }

    private fun mapImageProduct(
        data: List<TopSalesCoCategoriesProductsImageProductsEntity>
    ): ImagesProducts {
        return ImagesProducts(
            codsap = data[0].codsap,
            photoProduct = data[0].photoProduct
        )
    }
}
