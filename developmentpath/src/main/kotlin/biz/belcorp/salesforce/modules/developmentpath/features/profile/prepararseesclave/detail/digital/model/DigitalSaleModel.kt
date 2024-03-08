package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.model

import androidx.annotation.DrawableRes
import biz.belcorp.salesforce.modules.developmentpath.core.data.utils.Constant
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.digital.helper.DigitalViewTypeIdentifier

data class DigitalSaleModel(
    @DrawableRes val image: Int,
    val digitalId: Int = 0,
    val title: String,
    val subtitle: String,
    val summary: String? = Constant.EMPTY_STRING,
    @DigitalViewTypeIdentifier val type: Int = 0,
    val itemList: List<DigitalSaleItemModel>
)
