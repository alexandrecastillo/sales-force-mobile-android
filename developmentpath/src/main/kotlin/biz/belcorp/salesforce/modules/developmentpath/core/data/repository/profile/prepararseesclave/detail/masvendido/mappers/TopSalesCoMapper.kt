package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.profile.prepararseesclave.detail.masvendido.mappers

import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoBrandCategoriesCategoryEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoBrandCategoriesEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoBrandsEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoCategoriesEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoCategoriesProductsBuyCampaignEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoCategoriesProductsEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoCategoriesProductsImageProductsEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoEsikaEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoEsikaHistoryEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoEsikaImageEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoMultiCategoryEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoMultiMarkEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoTopProductEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoTriLbelEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoTriLbelStayEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoTrioLbelHistoryEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoTrioLbelHistoryStayEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoTrioLbelImageEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesCoTrioLbelStayImageEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesConsultantTopProductHistoryEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesMultiCategoryHistoryEntity
import biz.belcorp.salesforce.core.entities.profile.preparingiskey.salesconsultant.TopSalesMultiMarkHistoryEntity
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.co.ImagesProduct
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.co.TopSalesCoDto
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.common.Brand
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.common.Product

class TopSalesCoMapper {

    fun map(items: List<TopSalesCoDto.SalesCo>) = items.map { map(it) }

    private fun map(entity: TopSalesCoDto.SalesCo): TopSalesCoEntity =
        with(entity) {
            val topSalesConsultantEntity = TopSalesCoEntity(
                campaign = campaign,
                region = region,
                zone = zone,
                section = section,
                consultantCode = consultantCode,
                brandToPromote = brandToPromote,
                topSellingBrand = topSellingBrand
            )

            val brandCategories = brandCategories.map { mapBrandCategoryEntity(it) }
            val brands = brands.map { mapToBrandEntity(it) }
            val categories = categories.map { mapToCategoryEntity(it) }
            val products = topProducts.map { mapToTopProductEntity(it) }
            val multiMarks = multiMarks.map { mapToMultiMarkEntity(it) }
            val multiCategories = multiCategories.map { mapToMultiCategoriesEntity(it) }
            val trioLbel = trioLbel.map { mapToTioLbelEntity(it) }
            val esika = esikaMoreBeautiful.map { mapToEsikaEntity(it) }
            val trioLbelStay = trioLbelStay.map { mapToTioLbelStayEntity(it) }



            topSalesConsultantEntity.brandCategories.addAll(brandCategories)
            topSalesConsultantEntity.brands.addAll(brands)
            topSalesConsultantEntity.categories.addAll(categories)
            topSalesConsultantEntity.topProducts.addAll(products)
            topSalesConsultantEntity.multiMark.addAll(multiMarks)
            topSalesConsultantEntity.multiCategories.addAll(multiCategories)
            topSalesConsultantEntity.trioLbel.addAll(trioLbel)
            topSalesConsultantEntity.esika.addAll(esika)
            topSalesConsultantEntity.trioLbelStay.addAll(trioLbelStay)


            return topSalesConsultantEntity
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

    private fun mapToMultiMarkEntity(multiMark: TopSalesCoDto.MultiMark): TopSalesCoMultiMarkEntity = with(multiMark) {
        val entity = TopSalesCoMultiMarkEntity(
            order = order,
            name = name
        )

        val histories = histories.map { mapToHistoryMultiMarkEntity(it) }

        entity.historyMultiMark.addAll(histories)

        return entity
    }


    private fun mapToHistoryMultiMarkEntity(history: TopSalesCoDto.MultiMark.Historical) = with(history) {
        return@with TopSalesMultiMarkHistoryEntity(
            campaign = campaign,
            multimarca = multimarca
        )
    }

    private fun mapToCategoryEntity(category: TopSalesCoDto.Category): TopSalesCoCategoriesEntity =
        with(category) {
            val entity = TopSalesCoCategoriesEntity(
                name = name,
                units = units
            )

            val products = categories.map { mapToCategoriesProductsEntity(it) }

            entity.categoriesProducts.addAll(products)
            return entity
        }

    private fun mapToCategoriesProductsEntity(products: TopSalesCoDto.Category.Products): TopSalesCoCategoriesProductsEntity {


        val topSalesCoCategoriesProductsEntity = TopSalesCoCategoriesProductsEntity(
            order = products.order,
            name = products.name,
            sku = products.sku,
            cuv = products.cuv,
            quantity = products.quantity,
            amount = products.amount,
        )

        val buyCampaigns = products.buyCampaign.map { mapToBuyCampaignEntity(it) }
        topSalesCoCategoriesProductsEntity.buyCampaignsProductParent.addAll(buyCampaigns)

        val images = mapToImageProductEntity(products.images)
        topSalesCoCategoriesProductsEntity.imagesProductParent.add(images)

        return topSalesCoCategoriesProductsEntity
    }

    private fun mapToBuyCampaignEntity(buyCampaign: TopSalesCoDto.Category.Products.BuyCampaigns): TopSalesCoCategoriesProductsBuyCampaignEntity {
        return with(buyCampaign) {
            return@with TopSalesCoCategoriesProductsBuyCampaignEntity(
                campaign1 = campaign1,
                campaign2 = campaign2,
                campaign3 = campaign3
            )
        }
    }

    private fun mapToImageProductEntity(images: ImagesProduct): TopSalesCoCategoriesProductsImageProductsEntity {
        return with(images) {
            return@with TopSalesCoCategoriesProductsImageProductsEntity(
                codsap = codsap,
                photoProduct = photoProduct
            )
        }
    }


    private fun mapToBrandEntity(brand: Brand) =
        with(brand) {
            return@with TopSalesCoBrandsEntity(
                name = name,
                units = units
            )
        }

    private fun mapBrandCategoryEntity(brandCategory: TopSalesCoDto.BrandCategory): TopSalesCoBrandCategoriesEntity {

        val entity = TopSalesCoBrandCategoriesEntity(
            brand = brandCategory.brand,
            units = brandCategory.units
        )

        val categoriesCategory = brandCategory.category.map { mapToCategoriesCategory(it) }
        entity.categories.addAll(categoriesCategory)

        return entity
    }

    private fun mapToCategoriesCategory(brandCategoryCategory: TopSalesCoDto.BrandCategory.Category): TopSalesCoBrandCategoriesCategoryEntity {
        return TopSalesCoBrandCategoriesCategoryEntity(
            order = brandCategoryCategory.order,
            name = brandCategoryCategory.name,
            units = brandCategoryCategory.units
        )
    }


    private fun mapToMultiCategoriesEntity(multiCategories: TopSalesCoDto.MultiCategories): TopSalesCoMultiCategoryEntity = with(multiCategories) {
        val entity = TopSalesCoMultiCategoryEntity(
            order = order,
            category = category
        )

        val histories = histories.map { mapToHistoryMultiCategoryEntity(it) }

        entity.historyMultiCategory.addAll(histories)

        return entity
    }

    private fun mapToHistoryMultiCategoryEntity(history: TopSalesCoDto.MultiCategories.Historical) = with(history) {
        return@with TopSalesMultiCategoryHistoryEntity(
            campaign = campaign,
            bought = bought
        )
    }

    private fun mapToTioLbelEntity(trioLbel: TopSalesCoDto.TrioLbel): TopSalesCoTriLbelEntity = with(trioLbel) {
        val entity = TopSalesCoTriLbelEntity(
            order = order,
            name = name,
            sku = sku,
            cuv = cuv,
        )

        val histories = histories.map { mapToHistoryTrioLbelEntity(it) }
        entity.historyTrioLbel.addAll(histories)

        val images = mapToImagesTrioLbelProductEntity(images)
        entity.imagesProductParent.addAll(listOf(images))

        return entity
    }

    private fun mapToHistoryTrioLbelEntity(history: TopSalesCoDto.TrioLbel.Historical) = with(history) {
        return@with TopSalesCoTrioLbelHistoryEntity(
            campaign = campaign,
            bought = bought
        )
    }

    private fun mapToEsikaEntity(esikaMoreBeautiful: TopSalesCoDto.EsikaMoreBeautiful): TopSalesCoEsikaEntity = with(esikaMoreBeautiful) {
        val entity = TopSalesCoEsikaEntity(
            order = order,
            name = name,
            sku = sku,
            cuv = cuv,
        )

        val histories = histories.map { mapToHistoryEsikaEntity(it) }
        entity.historyEsika.addAll(histories)

        val images = mapToImagesEsikaProductEntity(images)
        entity.imagesProductParent.addAll(listOf(images))

        return entity
    }

    private fun mapToHistoryEsikaEntity(history: TopSalesCoDto.EsikaMoreBeautiful.Historical) = with(history) {
        return@with TopSalesCoEsikaHistoryEntity(
            campaign = campaign,
            bought = bought
        )
    }

    private fun mapToTioLbelStayEntity(trioLbel: TopSalesCoDto.TrioLbelStay): TopSalesCoTriLbelStayEntity = with(trioLbel) {
        val entity = TopSalesCoTriLbelStayEntity(
            order = order,
            name = name,
            sku = sku,
            cuv = cuv,
        )

        val histories = histories.map { mapToHistoryTrioLbelStayEntity(it) }
        entity.historyTrioLbelStay.addAll(histories)


        val images = mapToImagesTrioLbelStayProductEntity(images)
        entity.imagesProductParent.addAll(listOf(images))


        return entity
    }

    private fun mapToHistoryTrioLbelStayEntity(history: TopSalesCoDto.TrioLbelStay.Historical) = with(history) {
        return@with TopSalesCoTrioLbelHistoryStayEntity(
            campaign = campaign,
            bought = bought
        )
    }

    private fun mapToImagesTrioLbelStayProductEntity(images: ImagesProduct) = with(images) {
        return@with TopSalesCoTrioLbelStayImageEntity(
            codsap = codsap,
            photoProduct = photoProduct
        )
    }

    private fun mapToImagesEsikaProductEntity(images: ImagesProduct) = with(images) {
        return@with TopSalesCoEsikaImageEntity(
            codsap = codsap,
            photoProduct = photoProduct
        )
    }

    private fun mapToImagesTrioLbelProductEntity(images: ImagesProduct) = with(images) {
        return@with TopSalesCoTrioLbelImageEntity(
            codsap = codsap,
            photoProduct = photoProduct
        )
    }

}
