package biz.belcorp.salesforce.modules.billing.features.billing.mapper.detail

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.constants.NewCycleType
import biz.belcorp.salesforce.core.constants.NewCycleType.NewCycleType.HIGH_NEW_CYCLE_4D4
import biz.belcorp.salesforce.core.constants.NewCycleType.NewCycleType.HIGH_NEW_CYCLE_5D5
import biz.belcorp.salesforce.core.constants.NewCycleType.NewCycleType.HIGH_NEW_CYCLE_6D6
import biz.belcorp.salesforce.core.constants.NewCycleType.NewCycleType.NEW_CYCLE_2D2
import biz.belcorp.salesforce.core.constants.NewCycleType.NewCycleType.NEW_CYCLE_3D3
import biz.belcorp.salesforce.core.constants.NewCycleType.NewCycleType.NEW_CYCLE_4D4
import biz.belcorp.salesforce.core.constants.NewCycleType.NewCycleType.NEW_CYCLE_5D5
import biz.belcorp.salesforce.core.constants.NewCycleType.NewCycleType.NEW_CYCLE_6D6
import biz.belcorp.salesforce.modules.billing.core.domain.entities.BillingRules
import biz.belcorp.salesforce.modules.billing.core.domain.entities.NewCycleBilling
import biz.belcorp.salesforce.modules.billing.features.billing.model.detail.BillingNewCycleModel
import biz.belcorp.salesforce.modules.billing.features.billing.view.BillingDetailTextResolver
import kotlin.math.absoluteValue

class BillingNewCycleMapper(private val textResolver: BillingDetailTextResolver) {

    private val newCycleTypeList = listOf(
        NEW_CYCLE_6D6,
        HIGH_NEW_CYCLE_6D6,
        NEW_CYCLE_5D5,
        HIGH_NEW_CYCLE_5D5,
        NEW_CYCLE_4D4,
        HIGH_NEW_CYCLE_4D4,
        NEW_CYCLE_3D3,
        NEW_CYCLE_2D2
    )

    fun map(
        isThirdPerson: Boolean,
        models: List<NewCycleBilling>
    ): Pair<String, List<BillingNewCycleModel>> {
        val title = textResolver.getNewCycleTitle(isThirdPerson)
        return Pair(title, map(models))
    }

    private fun map(models: List<NewCycleBilling>): List<BillingNewCycleModel> {
        if (models.size < BillingRules.MAXIMUM_CAMPAIGNS) return listOf()
        val previous = models.first()
        val actual = models.last()
        return newCycleTypeList
            .map { map(previous, actual, it) }
    }

    private fun map(
        previous: NewCycleBilling,
        actual: NewCycleBilling,
        @NewCycleType type: Int
    ): BillingNewCycleModel {
        val calculatedProgress = getProgress(previous, actual, type)
        val progress =
            if (calculatedProgress > Constant.NUMERO_CIEN) Constant.NUMERO_CIEN else calculatedProgress
        return BillingNewCycleModel(
            title = getNewCycleTile(type),
            description = getNewCycleDescription(previous, actual, type),
            maxProgress = Constant.NUMERO_CIEN,
            progress = progress,
            progressLabel = textResolver.getNumberWithPercentage(progress),
            order = type
        )
    }

    private fun getNewCycleTile(@NewCycleType type: Int): String {
        return when (type) {
            NEW_CYCLE_2D2,
            NEW_CYCLE_3D3,
            NEW_CYCLE_4D4,
            NEW_CYCLE_5D5,
            NEW_CYCLE_6D6 -> textResolver.getNewCycleBillingTitle(type)
            HIGH_NEW_CYCLE_4D4 -> textResolver.getNewCycleHighValueBillingTitle(NEW_CYCLE_4D4)
            HIGH_NEW_CYCLE_5D5 -> textResolver.getNewCycleHighValueBillingTitle(NEW_CYCLE_5D5)
            HIGH_NEW_CYCLE_6D6 -> textResolver.getNewCycleHighValueBillingTitle(NEW_CYCLE_6D6)
            else -> Constant.EMPTY_STRING
        }
    }

    private fun getNewCycleDescription(
        previous: NewCycleBilling,
        actual: NewCycleBilling,
        @NewCycleType type: Int
    ): String {
        val values = when (type) {
            NEW_CYCLE_2D2 -> Pair(actual.lowValueOrders2d2, previous.lowValueOrders1d1)
            NEW_CYCLE_3D3 -> Pair(actual.lowValueOrders3d3, previous.lowValueOrders2d2)
            NEW_CYCLE_4D4 -> Pair(actual.lowValueOrders4d4, previous.lowValueOrders3d3)
            HIGH_NEW_CYCLE_4D4 -> Pair(actual.highValueOrders4d4, actual.highValueOrders4d4)
            NEW_CYCLE_5D5 -> Pair(actual.lowValueOrders5d5, previous.lowValueOrders4d4)
            HIGH_NEW_CYCLE_5D5 -> Pair(actual.highValueOrders5d5, previous.highValueOrders4d4)
            NEW_CYCLE_6D6 -> Pair(actual.lowValueOrders6d6, previous.lowValueOrders5d5)
            HIGH_NEW_CYCLE_6D6 -> Pair(actual.highValueOrders6d6, previous.highValueOrders5d5)
            else -> Pair(Constant.NUMERO_CERO, Constant.NUMERO_CERO)
        }
        return textResolver.getNewCycleBillingDescription(values.first.absoluteValue, values.second)
    }

    private fun getProgress(
        previous: NewCycleBilling,
        actual: NewCycleBilling,
        @NewCycleType type: Int
    ): Int {
        return when (type) {
            NEW_CYCLE_2D2 -> calculateCurrentProgress(actual.lowValueOrders2d2, previous.lowValueOrders1d1)
            NEW_CYCLE_3D3 -> calculateCurrentProgress(actual.lowValueOrders3d3, previous.lowValueOrders2d2)
            NEW_CYCLE_4D4 -> calculateCurrentProgress(actual.lowValueOrders4d4, previous.lowValueOrders3d3)
            HIGH_NEW_CYCLE_4D4 -> calculateCurrentProgress(actual.highValueOrders4d4, actual.highValueOrders4d4)
            NEW_CYCLE_5D5 -> calculateCurrentProgress(actual.lowValueOrders5d5, previous.lowValueOrders4d4)
            HIGH_NEW_CYCLE_5D5 -> calculateCurrentProgress(actual.highValueOrders5d5, previous.highValueOrders4d4)
            NEW_CYCLE_6D6 -> calculateCurrentProgress(actual.lowValueOrders6d6, previous.lowValueOrders5d5)
            HIGH_NEW_CYCLE_6D6 -> calculateCurrentProgress(actual.highValueOrders6d6, previous.highValueOrders5d5)
            else -> calculateCurrentProgress(Constant.NUMERO_CERO, Constant.NUMERO_CERO)
        }
    }

    private fun calculateCurrentProgress(currentValue: Int, totalValue: Int): Int {
        if (totalValue == Constant.NUMERO_CERO) return Constant.NUMERO_CERO
        return currentValue * Constant.NUMERO_CIEN / totalValue
    }
}
