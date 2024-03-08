package biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.view.common

import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.utils.formatWithThousands
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.collection.CollectionIndicator
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.collection.CollectionOrderRange
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.collection.CollectionTextResolver
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.model.ProgressBarGridModel
import java.util.ArrayList

open class CollectionMapper(private val textResolver: CollectionTextResolver) {

    fun createChargedOrderList(collection: CollectionIndicator): List<ProgressBarGridModel> {


        val ordersRangeFiltered = collection.ordersRange.filter {
            !it.range.equals("Bajo valor plus", true)
        }

        val ordersRange = if(ordersRangeFiltered.isNotEmpty()){
            ordersRangeFiltered
        }else{
            getDefaultList()
        }


        val sortedList = ordersRange.sortedBy { it.position }
        val twoLines = sortedList.any { it.total >= Constant.NUMBER_ONE_THOUSAND }
        return sortedList.map { createRanges(it.range, it.collected, it.total, twoLines) }
    }

    private fun createRanges(
        title: String,
        currentValue: Int,
        totalValue: Int,
        twoLines: Boolean
    ): ProgressBarGridModel {
        val currentVal = currentValue.formatWithThousands()
        val totalVal = totalValue.formatWithThousands()
        val value = textResolver.formatRangeSummary(twoLines, currentVal, totalVal)
        return ProgressBarGridModel(title, value, currentValue, totalValue)
    }

    private fun getDefaultList(): List<CollectionOrderRange> {
        return listOf(
            CollectionOrderRange(textResolver.getLowValueOrdersText()),
            CollectionOrderRange(textResolver.getHighValueOrdersText()),
            CollectionOrderRange(textResolver.getHighValuePlusOrdersText())
        )
    }

}
