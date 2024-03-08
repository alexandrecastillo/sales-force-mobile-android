package biz.belcorp.salesforce.modules.kpis.features.kpis.mapper.capitalization

import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ONE
import biz.belcorp.salesforce.core.constants.EmojiCode
import biz.belcorp.salesforce.core.utils.createEmoji
import biz.belcorp.salesforce.core.utils.formatWithThousands
import biz.belcorp.salesforce.core.utils.takeLastTwo
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiContainer
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.capitalization.CapitalizationIndicator
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.capitalization.RetentionCapiTextResolver
import biz.belcorp.salesforce.modules.kpis.features.kpis.KpiViewType
import biz.belcorp.salesforce.modules.kpis.features.kpis.models.KpiCapitalizationBillingModel
import biz.belcorp.salesforce.modules.kpis.features.kpis.models.KpiCapitalizationSaleModel
import biz.belcorp.salesforce.modules.kpis.features.kpis.models.KpiModel

class KpiCapitalizationMultiProfileMapper(private val textResolver: RetentionCapiTextResolver) {

    fun createKpiCapitalization(kpiContainer: KpiContainer): KpiModel = with(kpiContainer) {
        return if (isSale) createKpiCapitalizationSale(this)
        else createKpiCapitalizationBilling(this)
    }

    private fun createKpiCapitalizationSale(kpiContainer: KpiContainer): KpiModel =
        with(kpiContainer) {
            val actual = capitalizations.currentData
                ?: CapitalizationIndicator(campaign = capitalizations.currentCampaign)
            return KpiCapitalizationSaleModel(
                code = NUMBER_ONE,
                iconRes = R.drawable.ic_kpis_retention_capi,
                iconColor = R.color.colorIcon,
                backgroundColor = R.color.colorRetentionCapi,
                header = textResolver.formatHeader(),
                type = KpiViewType.KPI_TYPE_CAPITALIZATION_MULTIPROFILE_SALE,
                order = NUMBER_ONE,
                isThirdPerson = isThirdPerson,
                title = textResolver.formatTitle(
                    actual.campaign.takeLastTwo(),
                    isThirdPerson = isThirdPerson
                ),
                descriptionFirst = textResolver.formatDescription(
                    actual.capitalizationGoal.formatWithThousands(),
                    createEmoji(EmojiCode.DIRECT_HIT)
                ),
                subtitleSecond = textResolver.formatDescriptionBillingMultiProfile(
                    isThirdPerson = isThirdPerson
                ),
                descriptionSecond = textResolver.formatDescriptionRetention(
                    actual.pegRetentionGoal,
                    createEmoji(EmojiCode.DIRECT_HIT)
                )
            )
        }

    private fun createKpiCapitalizationBilling(kpiContainer: KpiContainer): KpiModel =
        with(kpiContainer) {
            val actual = capitalizations.currentData
                ?: CapitalizationIndicator(campaign = capitalizations.currentCampaign)
            return KpiCapitalizationBillingModel(
                code = NUMBER_ONE,
                iconRes = R.drawable.ic_kpis_retention_capi,
                iconColor = R.color.colorIcon,
                backgroundColor = R.color.colorRetentionCapi,
                header = textResolver.formatHeader(),
                type = KpiViewType.KPI_TYPE_CAPITALIZATION_MULTIPROFILE_BILLING,
                order = NUMBER_ONE,
                isThirdPerson = isThirdPerson,
                subtitleFirst = textResolver.formatSubTitleEntriesBilling(isThirdPerson),
                descriptionFirst = textResolver.formatCapitalizationProjected(
                    actual.capitalizationProjected.formatWithThousands()
                ),
                subtitleSecond = textResolver.formatSubTitleBilling(
                    isThirdPerson = isThirdPerson
                ),
                descriptionSecond = textResolver.formatDescriptionRetentionBilling(
                    actual.pegRetentionReal.formatWithThousands(),
                    actual.pegRetentionGoal.formatWithThousands(),
                    createEmoji(EmojiCode.DIRECT_HIT)
                )
            )
        }
}
