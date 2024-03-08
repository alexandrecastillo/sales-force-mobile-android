package biz.belcorp.salesforce.modules.billing.features.billing.mapper.detail

import androidx.core.content.ContextCompat
import biz.belcorp.mobile.components.design.indicatorgoalbar.IndicatorGoalBar
import biz.belcorp.salesforce.modules.billing.core.domain.entities.PegsBilling
import biz.belcorp.salesforce.modules.billing.features.billing.view.BillingDetailTextResolver
import biz.belcorp.salesforce.components.R as ComponentsR

class BillingPegsMapper(private val textResolver: BillingDetailTextResolver) {

    fun map(isThirdPerson: Boolean, model: PegsBilling): IndicatorGoalBar.Model = with(model) {
        return IndicatorGoalBar.Model(
            title = textResolver.getRetainedPegsTitle(isThirdPerson),
            type = IndicatorGoalBar.GoalType.NORMAL,
            goalMessageColor = ContextCompat.getColor(
                textResolver.context,
                ComponentsR.color.magenta
            ),
            maxProgress = totalPegs,
            currentProgress = retainedPegs,
            goalDescription = textResolver.getRetainedPegsDescription(model.totalPegs),
            goalMessage = textResolver.getRetainedPegsMessage(),
            currentTarget = textResolver.getPendingPegsTargetDescription(retainedPegs)
        )
    }
}
