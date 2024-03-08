package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.mappers

import android.text.Spannable
import biz.belcorp.salesforce.core.constants.EmojiCode
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.ventas.CatalogSaleConsultant
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.model.CatalogSaleConsultantModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.model.SaleModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.view.SaleConsultantTextResolver
import biz.belcorp.salesforce.base.R as BaseR

class CatalogSaleConsultantMapper(private val textResolver: SaleConsultantTextResolver) {

    fun map(entities: List<CatalogSaleConsultant>): CatalogSaleConsultantModel {
        if (entities.isEmpty()) return CatalogSaleConsultantModel()
        val catalogSales = entities.sortedByDescending { it.campaign }.map(::map)
        return CatalogSaleConsultantModel(
            sales = catalogSales,
            amountAverage = entities.last().amountAverageSaleLastSixCampaigns.formatWithLowerThousands(),
            billedAmount = entities.last().amountBilledOrders.numberFormatUS(),
            firstBillingCampaign = entities.last().billingFirstCampaign,
            lastBillingCampaign = entities.last().billingLastCampaign
        ).apply {
            lastSale = map(entities.last())
            subtitle = createSubTitle(entities.last())
        }
    }

    fun map(entity: CatalogSaleConsultant): SaleModel {
        return SaleModel(
            campaign = textResolver.formatCampaignWithPrefix(entity.campaign.takeLastTwo()),
            amount = entity.amount.numberFormatUS(),
            isAmountZero = entity.amount.isZero(),
            color = getColor(entity.isHighValueOrder, entity.amount.isZero())
        )
    }

    private fun getColor(isHighOrderValue: Boolean, isZero: Boolean): Int {
        val color = if (isHighOrderValue && !isZero) BaseR.color.positivo else BaseR.color.negativo
        return textResolver.context.getCompatColor(color)
    }

    private fun createSubTitle(model: CatalogSaleConsultant): Spannable {
        val campaign = textResolver.formatCampaignWithPrefix(model.campaign.takeLastTwo())
        val color = if (model.isHighValueOrder) BaseR.color.positivo else BaseR.color.negativo
        val emoji = if (model.isHighValueOrder) EmojiCode.MUSCLE else EmojiCode.DISAPPOINTED_FACE
        val text =
            if (model.isHighValueOrder) textResolver.getHighValueOrder() else textResolver.getLowValueOrder()
        return spannable {
            span(createEmoji(emoji)) + space() + span(campaign) + space() +
                    color(text, textResolver.context.getCompatColor(color))
        }
    }
}
