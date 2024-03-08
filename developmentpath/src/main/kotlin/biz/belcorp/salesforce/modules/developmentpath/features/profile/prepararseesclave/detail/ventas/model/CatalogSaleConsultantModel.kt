package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.model

import android.text.Spannable
import androidx.annotation.ColorInt
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.utils.span

class CatalogSaleConsultantModel(
    val sales: List<SaleModel> = emptyList(),
    val amountAverage: String = EMPTY_STRING,
    val firstBillingCampaign: String = EMPTY_STRING,
    val lastBillingCampaign: String = EMPTY_STRING,
    val billedAmount: String = EMPTY_STRING
) {
    val hasSales get() = sales.isNotEmpty()
    var lastSale: SaleModel? = null
    var subtitle: Spannable = span(EMPTY_STRING)
}

class SaleModel(
    val campaign: String,
    @ColorInt val color: Int,
    val amount: String,
    val isAmountZero: Boolean
)

