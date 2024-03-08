package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.co

import biz.belcorp.salesforce.core.constants.KEY_CAMPAIGN
import biz.belcorp.salesforce.core.constants.KEY_REGION
import biz.belcorp.salesforce.core.constants.KEY_SECTION
import biz.belcorp.salesforce.core.constants.KEY_ZONE
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.*
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.common.Brand
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.common.Product
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopSalesCoDto(
    @SerialName(COLLECTION_KEY)
    val list: List<SalesCo> = emptyList()
) {

    @Serializable
    data class SalesCo(
        @SerialName(KEY_CAMPAIGN)
        val campaign: String,
        @SerialName(KEY_REGION)
        val region: String,
        @SerialName(KEY_ZONE)
        val zone: String,
        @SerialName(KEY_SECTION)
        val section: String,
        @SerialName(KEY_CONSULTANT_CODE)
        val consultantCode: String,
        @SerialName(KEY_TOP_SELLING_BRAND)
        val topSellingBrand: String,
        @SerialName(KEY_BRAND_TO_PROMOTE)
        val brandToPromote: String,
        @SerialName(KEY_BRANDS_CATEGORIES)
        val brandCategories: List<BrandCategory>,
        @SerialName(KEY_BRANDS)
        val brands: List<Brand>,
        @SerialName(KEY_CATEGORIES)
        val categories: List<Category>,
        @SerialName(KEY_TOP_PRODUCTS)
        val topProducts: List<Product>,
        @SerialName(KEY_MULTI_MARK)
        val multiMarks: List<MultiMark>,
        @SerialName(KEY_MULTI_CATEGORY)
        val multiCategories: List<MultiCategories>,
        @SerialName(KEY_TRIO_LBEL)
        val trioLbel: List<TrioLbel>,
        @SerialName(KEY_ESIKA_MORE_BEAUTIFUL)
        val esikaMoreBeautiful: List<EsikaMoreBeautiful>,
        @SerialName(KEY_TRIO_LBEL_STAY)
        val trioLbelStay: List<TrioLbelStay>
    )

    @Serializable
    data class BrandCategory(
        @SerialName(KEY_BRAND)
        val brand: String,
        @SerialName(KEY_CATEGORY)
        val category: List<Category>,
        @SerialName(KEY_UNITS)
        val units: Int
    ) {
        @Serializable
        data class Category(
            @SerialName(KEY_NAME)
            val name: String,
            @SerialName(KEY_UNITS)
            val units: Int,
            @SerialName(KEY_ORDER)
            val order: Int
        )
    }

    @Serializable
    data class Category(
        @SerialName(KEY_NAME)
        val name: String,
        @SerialName(KEY_UNITS)
        val units: Int,
        @SerialName(KEY_PRODUCTS)
        val categories: List<Products>,
    ) {
        @Serializable
        data class Products(
            @SerialName(KEY_ORDER)
            val order: Int,
            @SerialName(KEY_NAME)
            val name: String,
            @SerialName(KEY_SKU)
            val sku: String,
            @SerialName(KEY_CUV)
            val cuv: String,
            @SerialName(KEY_BUY_CAMPAIGN)
            val buyCampaign: List<BuyCampaigns>,
            @SerialName(KEY_QUANTITY)
            val quantity: Int,
            @SerialName(KEY_AMOUNT)
            val amount: Double,
            @SerialName(KEY_IMAGES)
            val images: ImagesProduct
        ) {
            @Serializable
            data class BuyCampaigns(
                @SerialName(KEY_BUY_CAMPAIGN_1)
                val campaign1: String,
                @SerialName(KEY_BUY_CAMPAIGN_2)
                val campaign2: String,
                @SerialName(KEY_BUY_CAMPAIGN_3)
                val campaign3: String,
            )
        }
    }

    @Serializable
    data class MultiMark(
        @SerialName(KEY_ORDER)
        val order: Int,
        @SerialName(KEY_NAME)
        val name: String,
        @SerialName(KEY_HISTORY)
        val histories: List<Historical>
    ) {
        @Serializable
        data class Historical(
            @SerialName(KEY_CAMPAIGN)
            val campaign: String,
            @SerialName(KEY_MULTI_MARK)
            val multimarca: Int
        )
    }

    @Serializable
    data class MultiCategories(
        @SerialName(KEY_ORDER)
        val order: Int,
        @SerialName(KEY_CATEGORY)
        val category: String,
        @SerialName(KEY_HISTORY)
        val histories: List<Historical>
    ) {
        @Serializable
        data class Historical(
            @SerialName(KEY_CAMPAIGN)
            val campaign: String,
            @SerialName(KEY_BOUGHT)
            val bought: Int
        )
    }

    @Serializable
    data class TrioLbel(
        @SerialName(KEY_ORDER)
        val order: Int,
        @SerialName(KEY_NAME)
        val name: String,
        @SerialName(KEY_SKU)
        val sku: String,
        @SerialName(KEY_CUV)
        val cuv: String,
        @SerialName(KEY_IMAGES)
        val images: ImagesProduct,
        @SerialName(KEY_HISTORY)
        val histories: List<Historical>
    ) {
        @Serializable
        data class Historical(
            @SerialName(KEY_CAMPAIGN)
            val campaign: String,
            @SerialName(KEY_BOUGHT)
            val bought: Int
        )
    }

    @Serializable
    data class TrioLbelStay(
        @SerialName(KEY_ORDER)
        val order: Int,
        @SerialName(KEY_NAME)
        val name: String,
        @SerialName(KEY_SKU)
        val sku: String,
        @SerialName(KEY_CUV)
        val cuv: String,
        @SerialName(KEY_IMAGES)
        val images: ImagesProduct,
        @SerialName(KEY_HISTORY)
        val histories: List<Historical>
    ) {
        @Serializable
        data class Historical(
            @SerialName(KEY_CAMPAIGN)
            val campaign: String,
            @SerialName(KEY_BOUGHT)
            val bought: Int
        )
    }

    @Serializable
    data class EsikaMoreBeautiful(
        @SerialName(KEY_ORDER)
        val order: Int,
        @SerialName(KEY_NAME)
        val name: String,
        @SerialName(KEY_SKU)
        val sku: String,
        @SerialName(KEY_CUV)
        val cuv: String,
        @SerialName(KEY_IMAGES)
        val images: ImagesProduct,
        @SerialName(KEY_HISTORY)
        val histories: List<Historical>
    ) {
        @Serializable
        data class Historical(
            @SerialName(KEY_CAMPAIGN)
            val campaign: String,
            @SerialName(KEY_BOUGHT)
            val bought: Int
        )
    }

}

@Serializable
data class ImagesProduct(
    @SerialName(KEY_CODSAP)
    val codsap: String,
    @SerialName(KEY_PHOTO_PRODUCT)
    val photoProduct: List<String>
)
