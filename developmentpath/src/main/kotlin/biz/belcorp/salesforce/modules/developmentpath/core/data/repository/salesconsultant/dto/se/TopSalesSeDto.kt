package biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.se

import biz.belcorp.salesforce.core.constants.KEY_CAMPAIGN
import biz.belcorp.salesforce.core.constants.KEY_REGION
import biz.belcorp.salesforce.core.constants.KEY_SECTION
import biz.belcorp.salesforce.core.constants.KEY_ZONE
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.KEY_BRANDS
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.KEY_TOP_PRODUCTS
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.common.Brand
import biz.belcorp.salesforce.modules.developmentpath.core.data.repository.salesconsultant.dto.common.Product
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TopSalesSeDto(
    @SerialName(COLLECTION_KEY)
    val list: List<TopSaleSe> = emptyList()
) {

    @Serializable
    data class TopSaleSe(
        @SerialName(KEY_CAMPAIGN)
        val campaign: String,
        @SerialName(KEY_REGION)
        val region: String,
        @SerialName(KEY_ZONE)
        val zone: String,
        @SerialName(KEY_SECTION)
        val section: String,
        @SerialName(KEY_BRANDS)
        val brands: List<Brand>,
        @SerialName(KEY_TOP_PRODUCTS)
        val topProducts: List<Product>
    )

}
