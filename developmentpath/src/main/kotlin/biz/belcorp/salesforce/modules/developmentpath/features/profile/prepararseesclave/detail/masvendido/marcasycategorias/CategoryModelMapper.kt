package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.masvendido.marcasycategorias

import biz.belcorp.salesforce.core.utils.isNotNull
import biz.belcorp.salesforce.core.utils.isNull
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant.CAMPAIGN_TITLE
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant.CATEGORY_PRODUCTS_UNITS_TITLE
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.masvendido.marcasycategorias.BuyCampaigns
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.masvendido.marcasycategorias.Category
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.masvendido.marcasycategorias.Product

class CategoryModelMapper {

    fun parse(entity: Category): CategoryModel {
        return CategoryModel(
            name = entity.name,
            units = entity.units,
            categoryProducts = mapCategoryProductsToModel(entity.categoriesProducts)
        )
    }

    private fun mapCategoryProductsToModel(categoriesProducts: List<Product>):
        List<CategoryProductModel> = categoriesProducts.map {
        val unitsString = StringBuilder()
        unitsString.append(CATEGORY_PRODUCTS_UNITS_TITLE)
        unitsString.append(it.quantity.toString())
        CategoryProductModel(
            amount = it.amount,
            name = it.name,
            order = it.order,
            sku = it.sku,
            cuv = it.sku,
            quantity = unitsString.toString(),
            buyCampaigns = mapBuyCampaignToModel(it.buyCampaigns),
            image = it.image
        )
    }

    private fun mapBuyCampaignToModel(buyCampaigns: List<BuyCampaigns>): String {
        val buyCampaignsString = StringBuilder()
        buyCampaignsString.append(CAMPAIGN_TITLE)
        if (buyCampaigns[0].isNotNull()) {
            val campaign1 = buyCampaigns[0].campaign1
            val campaign2 = buyCampaigns[0].campaign2
            val campaign3 = buyCampaigns[0].campaign3

            if ((campaign1.isNotNull() && campaign1.isNotBlank())
                && (campaign2.isNull() || campaign2.isBlank())
                && (campaign3.isNull() || campaign3.isBlank())
            ) {
                buyCampaignsString.append(campaign1)
            } else {
                if (campaign1.isNotNull() && campaign1.isNotBlank()) {
                    buyCampaignsString.append("$campaign1 -")

                }
            }
            if ((campaign2.isNotNull() && campaign2.isNotBlank())
                && (campaign1.isNull() || campaign1.isBlank())
                && (campaign3.isNull() || campaign3.isBlank())
            ) {
                buyCampaignsString.append(campaign2)
            } else {
                if (campaign2.isNotNull() && campaign2.isNotBlank()) {
                    buyCampaignsString.append("$campaign2 -")

                }
            }
            buyCampaignsString.append(campaign3)
        }
        return buyCampaignsString.toString()
    }
}
