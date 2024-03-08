package biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.mappers

import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import biz.belcorp.salesforce.core.constants.Constant.ZERO_DECIMAL
import biz.belcorp.salesforce.core.constants.EmojiCode
import biz.belcorp.salesforce.core.utils.createEmoji
import biz.belcorp.salesforce.core.utils.getCompatColor
import biz.belcorp.salesforce.core.utils.numberFormatUS
import biz.belcorp.salesforce.core.utils.takeLastTwo
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.ventas.GainConsultant
import biz.belcorp.salesforce.modules.developmentpath.core.domain.entities.prepararseesclavedetail.ventas.GainConsultantContainer
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.model.GainConsultantModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.model.GainModel
import biz.belcorp.salesforce.modules.developmentpath.features.profile.prepararseesclave.detail.ventas.view.SaleConsultantTextResolver
import biz.belcorp.salesforce.base.R as BaseR

class GainConsultantMapper(private val textResolver: SaleConsultantTextResolver) {

    fun map(entity: GainConsultantContainer): GainConsultantModel = with(entity) {
        if (gains.isEmpty()) return GainConsultantModel()
        val gainModels = gains.map { map(it, gainBest.campaign) }
        return GainConsultantModel(getSortedGains(gainModels)).apply {
            lastGain = map(gainLast, gainBest.campaign)
            subtitle = getSubtitle(gainLast.campaign)
        }
    }

    private fun map(gain: GainConsultant, bestCampaign: String): GainModel {
        val isMaximum = gain.campaign == bestCampaign
        return GainModel(
            campaign = textResolver.formatCampaignWithPrefix(gain.campaign.takeLastTwo()),
            amount = getAmount(gain.amount, isMaximum),
            isMaximumGain = isMaximum,
            color = getColor(gain.amount.compareTo(ZERO_DECIMAL) == NUMBER_ZERO)
        )
    }

    private fun getSortedGains(gains: List<GainModel>) = gains.sortedByDescending { it.campaign }

    private fun getSubtitle(campaign: String): String {
        return textResolver.formatGainSubTitle(EmojiCode.MONEY_BAG, campaign.takeLastTwo())
    }

    private fun getAmount(amount: Double, isMaximum: Boolean): String {
        return if (isMaximum) getAmountWithEmoji(amount, EmojiCode.BILL)
        else amount.numberFormatUS()
    }

    private fun getAmountWithEmoji(amount: Double, emoji: String): String {
        return textResolver.formatAmountWithEmoji(amount.numberFormatUS(), createEmoji(emoji))
    }

    private fun getColor(isZero: Boolean): Int {
        val color = if (isZero) BaseR.color.negativo else BaseR.color.black
        return textResolver.context.getCompatColor(color)
    }
}