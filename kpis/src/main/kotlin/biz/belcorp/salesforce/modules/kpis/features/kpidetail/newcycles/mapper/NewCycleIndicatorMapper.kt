package biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.mapper

import biz.belcorp.salesforce.base.utils.isSe
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.newcycle.NewCycleIndicator
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.newcycle.NewCycleIndicatorContainer
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.model.CoupledModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.model.ValueAttributes
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.NewCycleTextResolver
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.model.NewCycleIndicatorModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.model.ProgressModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.model.SegmentFeedModel

class NewCycleIndicatorMapper(
    private val textResolver: NewCycleTextResolver
) : NewCycleMapper(textResolver) {

    fun map(newCycleContainer: NewCycleIndicatorContainer): NewCycleIndicatorModel {
        return if (newCycleContainer.isBilling) {
            createNewCycleIndicatorBilling(newCycleContainer)
        } else {
            createNewCycleIndicatorSale(newCycleContainer)
        }
    }

    private fun createNewCycleIndicatorSale(newCycleContainer: NewCycleIndicatorContainer): NewCycleIndicatorModel = with(newCycleContainer) {
        val previousIndicator = listIndicators.previousData
            ?: NewCycleIndicator(campaign = listIndicators.previousCampaign)
        val lowValueSegments = createLowValueSegmentsSale(previousIndicator)
        val highValueSegments = createHighValueSegmentsSale(previousIndicator)
        val lowValueSegmentFeed = createSegmentFeed(
            segmentList = lowValueSegments,
            title = textResolver.getLowValueTitle(lowValueSegments.first().summary),
            spanItems = Constant.NUMBER_FOUR
        )
        val highValueSegmentFeed = createSegmentFeed(
            segmentList = highValueSegments,
            title = textResolver.getHighValueTitle(highValueSegments.first().summary),
            spanItems = Constant.NUMBER_ONE
        )
        return NewCycleIndicatorModel(
                lowValueSegments = lowValueSegments,
                highValueSegments = highValueSegments,
                retentionResults = createRetentionResults(role, previousIndicator, isThirdPerson),
                title = textResolver.getSaleTitleForNewCycle(role, currentCampaignNameOnly, isThirdPerson),
                segmentFeedListModel = listOf(lowValueSegmentFeed, highValueSegmentFeed),
                retentionResultsTitle = textResolver.getRetentionResultTitle(role, previousCampaignNameOnly, isThirdPerson),
                summary = createSummary(ToolTipParams(isThirdPerson, currencySymbol, bonusInfo.lowValueBonus, bonusInfo.highValueBonus)),
                region = previousIndicator.region,
                zone = previousIndicator.zone,
                section = previousIndicator.section,
                isBilling = isBilling
            )
    }

    private fun createSegmentFeed(segmentList: List<ProgressModel>, title: String, spanItems: Int): SegmentFeedModel {
        return SegmentFeedModel(
            title = title,
            list = segmentList.excludeFirst(),
            spanItems = spanItems
        )
    }

    private fun createNewCycleIndicatorBilling(newCycleContainer: NewCycleIndicatorContainer): NewCycleIndicatorModel = with(newCycleContainer) {
        val previousIndicator = listIndicators.previousData
            ?: NewCycleIndicator(campaign = listIndicators.previousCampaign)
        val currentIndicator = listIndicators.currentData
            ?: NewCycleIndicator(campaign = listIndicators.currentCampaign)
        val lowValueSegments = createLowValueSegmentsBilling(currentIndicator, previousIndicator)
        val highValueSegments = createHighValueSegmentsBilling(currentIndicator, previousIndicator)
        val lowValueSegmentFeed = createSegmentFeed(
            segmentList = lowValueSegments,
            title = textResolver.getLowValueTitle(lowValueSegments.first().summary),
            spanItems = if (role.isSe()) Constant.NUMBER_FOUR else Constant.NUMBER_TWO
        )
        val highValueSegmentFeed = createSegmentFeed(
            segmentList = highValueSegments,
            title = textResolver.getHighValueTitle(highValueSegments.first().summary),
            spanItems = Constant.NUMBER_TWO
        )
        return NewCycleIndicatorModel(
            lowValueSegments = lowValueSegments,
            highValueSegments = highValueSegments,
            segmentFeedListModel = listOf(lowValueSegmentFeed, highValueSegmentFeed),
            title = textResolver.getBillingTitleForNewCycle(currentCampaignNameOnly),
            summary = createSummary(ToolTipParams(isThirdPerson, currencySymbol, bonusInfo.lowValueBonus, bonusInfo.highValueBonus)),
            region = currentIndicator.region,
            zone = currentIndicator.zone,
            section = currentIndicator.section,
            isBilling = isBilling,
            hasWidthFitGrid = !role.isSe()
        )
    }

    private fun createRetentionResults(
        role: Rol,
        previousIndicator: NewCycleIndicator,
        isThirdPerson: Boolean
    ): List<CoupledModel> {
        return createLowRetentionResults(role, previousIndicator, isThirdPerson) +
            createHighRetentionResults(role, previousIndicator, isThirdPerson
        )
    }

    private fun createLowRetentionResults(
        role: Rol,
        previousIndicator: NewCycleIndicator,
        isThirdPerson: Boolean
    ): List<CoupledModel> {
        val (labelAppearance, valueAppearance) = getTextAppearanceByRole(role, isThirdPerson)
        val retentionList = arrayListOf<CoupledModel>()
        val isMultiProfile = role.isMultiProfile() && !isThirdPerson
        if (!isMultiProfile) {
            val lowValueRetained = CoupledModel.SingleItem(
                label = ValueAttributes(
                    value = textResolver.getLowValueRetainedTitle(),
                    textAppearanceInt = labelAppearance
                ),
                value = ValueAttributes(
                    value = previousIndicator.lowValueOrders6d6.toString(),
                    textAppearanceInt = valueAppearance
                )
            )
            retentionList.add(lowValueRetained)
        }
        val lowRetentionPercentage = CoupledModel.SingleItem(
            label = ValueAttributes(
                value = textResolver.getLowValueRetentionTitle().toUpperCase(isMultiProfile),
                textAppearanceInt = labelAppearance
            ),
            value = ValueAttributes(
                value = textResolver.formatPercentageStringLabel(previousIndicator.lowValueOrdersRetentionPercentage.toPercentageWithLowerThousands()),
                textAppearanceInt = valueAppearance
            )
        )
        retentionList.add(lowRetentionPercentage)
        return retentionList
    }

    private fun getTextAppearanceByRole(role: Rol, isThirdPerson: Boolean): Pair<Int, Int> {
        return if (role.isSe() || isThirdPerson) {
            R.style.TextAppearance_NewCycle_Single_Item_Label to R.style.TextAppearance_NewCycle_Single_Item_Value
        } else {
            R.style.TextAppearance_NewCycle_MultiProfile_Single_Item_Label to R.style.TextAppearance_NewCycle_MultiProfile_Single_Item_Value
        }
    }

    private fun createHighRetentionResults(
        role: Rol,
        previousIndicator: NewCycleIndicator,
        isThirdPerson: Boolean
    ): List<CoupledModel> {
        val (labelAppearance, valueAppearance) = getTextAppearanceByRole(role, isThirdPerson)
        val retentionList = arrayListOf<CoupledModel>()
        val isMultiProfile = !role.isSe() && !isThirdPerson
        if (!isMultiProfile) {
            val highValueRetained = CoupledModel.SingleItem(
                label = ValueAttributes(
                    value = textResolver.getHighValueRetainedTitle(),
                    textAppearanceInt = labelAppearance
                ),
                value = ValueAttributes(
                    value = previousIndicator.highValueOrders6d6.toString(),
                    textAppearanceInt = valueAppearance
                )
            )
            retentionList.add(highValueRetained)
        }
        val highRetentionPercentage = CoupledModel.SingleItem(
            label = ValueAttributes(
                value = textResolver.getHighValueRetentionTitle(role, isMultiProfile),
                textAppearanceInt = labelAppearance
            ),
            value = ValueAttributes(
                value = textResolver.formatPercentageStringLabel(previousIndicator.highValueOrdersRetentionPercentage.toPercentageWithLowerThousands()),
                textAppearanceInt = valueAppearance
            )
        )
        retentionList.add(highRetentionPercentage)
        return retentionList
    }

    private fun formatCurrency(currency: String, value: Double): String {
        return textResolver.formatCurrency(currency, value.formatWithLowerThousands())
    }

    private fun createSummary(toolTipParams: ToolTipParams): String = with(toolTipParams){
        return if (lowValueBonus.isZero() && highValueBonus.isZero()) {
            Constant.EMPTY_STRING
        } else {
            textResolver.getReward(
                lowValueBonus = formatCurrency(currencySymbol, lowValueBonus),
                highValueBonus = formatCurrency(currencySymbol, highValueBonus),
                isThirdPerson = isThirdPerson
            )
        }
    }

    internal class ToolTipParams(
        val isThirdPerson: Boolean,
        val currencySymbol: String,
        val lowValueBonus: Double,
        val highValueBonus: Double
    )
}
