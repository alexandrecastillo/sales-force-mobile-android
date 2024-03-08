package biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.mapper

import biz.belcorp.salesforce.core.utils.zeroIfNull
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.newcycle.NewCycleIndicator
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.NewCycleTextResolver
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.model.ProgressModel

open class NewCycleMapper(private val textResolver: NewCycleTextResolver) {
    protected fun createLowValueSegmentsSale(previousIndicator: NewCycleIndicator): List<ProgressModel> {
        val segment6D6 = createSegmentModel(
            textResolver.getTitle6d6(),
            previousIndicator.lowValueOrders5d5.toString()
        )
        val segment5D5 = createSegmentModel(
            textResolver.getTitle5d5(),
            previousIndicator.lowValueOrders4d4.toString()
        )
        val segment4D4 = createSegmentModel(
            textResolver.getTitle4d4(),
            previousIndicator.lowValueOrders3d3.toString()
        )
        val segment3D3 = createSegmentModel(
            textResolver.getTitle3d3(),
            previousIndicator.lowValueOrders2d2.toString()
        )
        val segment2D2 = createSegmentModel(
            textResolver.getTitle2d2(),
            previousIndicator.lowValueOrders1d1.toString()
        )
        return arrayListOf(segment6D6, segment5D5, segment4D4, segment3D3, segment2D2)
    }

    protected fun createHighValueSegmentsSale(previousIndicator: NewCycleIndicator): List<ProgressModel> {
        val highValueSegment6D6 = createSegmentModel(
            textResolver.getHighValueTitle6d6(),
            previousIndicator.highValueOrders5d5.toString()
        )
        val highValueSegment5D5 = createSegmentModel(
            textResolver.getHighValueTitle5d5(),
            previousIndicator.highValueOrders4d4.toString()
        )
        return arrayListOf(highValueSegment6D6, highValueSegment5D5)
    }

    protected fun createLowValueSegmentsBilling(
        currentIndicator: NewCycleIndicator,
        previousIndicator: NewCycleIndicator
    ): List<ProgressModel> {
        val segment6D6 = createSegmentModel(
            textResolver.getTitle6d6(),
            currentIndicator.lowValueOrders6d6.toString(),
            previousIndicator.lowValueOrders5d5.toString()
        )
        val segment5D5 = createSegmentModel(
            textResolver.getTitle5d5(),
            currentIndicator.lowValueOrders5d5.toString(),
            previousIndicator.lowValueOrders4d4.toString()
        )
        val segment4D4 = createSegmentModel(
            textResolver.getTitle4d4(),
            currentIndicator.lowValueOrders4d4.toString(),
            previousIndicator.lowValueOrders3d3.toString()
        )
        val segment3D3 = createSegmentModel(
            textResolver.getTitle3d3(),
            currentIndicator.lowValueOrders3d3.toString(),
            previousIndicator.lowValueOrders2d2.toString()
        )
        val segment2D2 = createSegmentModel(
            textResolver.getTitle2d2(),
            currentIndicator.lowValueOrders2d2.toString(),
            previousIndicator.lowValueOrders1d1.toString()
        )
        return arrayListOf(segment6D6, segment5D5, segment4D4, segment3D3, segment2D2)
    }

    protected fun createHighValueSegmentsBilling(
        currentIndicator: NewCycleIndicator,
        previousIndicator: NewCycleIndicator
    ): List<ProgressModel> {
        val segment6D6 = createSegmentModel(
            textResolver.getHighValueTitle6d6(),
            currentIndicator.highValueOrders6d6.toString(),
            previousIndicator.highValueOrders5d5.toString()
        )
        val segment5D5 = createSegmentModel(
            textResolver.getHighValueTitle5d5(),
            currentIndicator.highValueOrders5d5.toString(),
            previousIndicator.highValueOrders4d4.toString()
        )
        val segment4D4 = createSegmentModel(
            textResolver.getHighValueTitle4d4(),
            currentIndicator.highValueOrders4d4.toString(),
            currentIndicator.highValueOrders4d4.toString()
        )
        return listOf(segment6D6, segment5D5, segment4D4)
    }

    private fun createSegmentModel(
        title: String,
        currentVal: String,
        previousVal: String? = null
    ): ProgressModel {
        val summary = if (previousVal != null) {
            textResolver.formatSegmentText(currentVal, previousVal)
        } else {
            currentVal
        }
        return ProgressModel(
            title,
            summary,
            currentVal.toIntOrNull(),
            previousVal?.toInt().zeroIfNull()
        )
    }
}
