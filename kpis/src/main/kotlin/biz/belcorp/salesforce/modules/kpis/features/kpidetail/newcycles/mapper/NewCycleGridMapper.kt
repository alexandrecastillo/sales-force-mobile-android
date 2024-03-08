package biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.mapper

import biz.belcorp.mobile.components.design.spreadsheet.models.Column
import biz.belcorp.salesforce.base.utils.isDv
import biz.belcorp.salesforce.base.utils.isGr
import biz.belcorp.salesforce.base.utils.isGz
import biz.belcorp.salesforce.core.constants.Constant
import biz.belcorp.salesforce.core.domain.entities.ua.UaInfo
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.KpiRules
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.newcycle.NewCycleGridContainer
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.newcycle.NewCycleIndicator
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.NewCycleTextResolver
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.creator.NewCycleGridCreator
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.model.NewCycleConsolidated
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.newcycles.model.NewCycleGridIndicatorModel
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.shared.filtergrid.types.NewCycleGridType

class NewCycleGridMapper(
    val textResolver: NewCycleTextResolver,
    private val gridCreator: NewCycleGridCreator
) {

    fun map(
        container: NewCycleGridContainer,
        uaList: List<UaInfo>, @NewCycleGridType type: String
    ): List<Column> = with(container) {
        val consolidated = NewCycleConsolidated(
            uaList = uaList,
            values = createGridIndicatorList(uaList, this),
            role = role,
            isBilling = isBilling,
            isParent = isParent
        )
        return gridCreator.getColumns(consolidated, type)
    }

    private fun createGridIndicatorList(
        uaList: List<UaInfo>,
        container: NewCycleGridContainer
    ): List<NewCycleGridIndicatorModel> = with(container) {
        return getChildGridIndicatorList(uaList, container)
    }

    private fun getChildGridIndicatorList(
        uaList: List<UaInfo>,
        container: NewCycleGridContainer
    ): List<NewCycleGridIndicatorModel> = with(container) {
        val indicatorModelList = arrayListOf<NewCycleGridIndicatorModel>()
        getUaList(uaList, role).forEach { ua ->
            val listFiltered = childIndicatorList.filter { getUaChildByIndicator(role, it) == ua }
            val indicatorModel = createGridDataByType(listFiltered, isBilling)
            indicatorModelList.add(indicatorModel)
        }
        return@with indicatorModelList
    }

    private fun getUaList(uaList: List<UaInfo>, role: Rol): List<String> {
        return uaList.map { getUaIdByRole(it, role) }
    }

    private fun createGridDataByType(
        listIndicator: List<NewCycleIndicator>,
        isBilling: Boolean
    ): NewCycleGridIndicatorModel {
        return if (isBilling) {
            createNewCycleIndicatorBilling(listIndicator)
        } else {
            createNewCycleIndicatorSale(listIndicator)
        }
    }

    private fun getUaIdByRole(ua: UaInfo, role: Rol): String {
        return when {
            role.isDv() -> ua.uaKey.codigoRegion.orEmpty()
            role.isGr() -> ua.uaKey.codigoZona.orEmpty()
            role.isGz() -> ua.uaKey.codigoSeccion.orEmpty()
            else -> Constant.EMPTY_STRING
        }
    }

    private fun getUaChildByIndicator(role: Rol, newCycleIndicator: NewCycleIndicator): String {
        return when {
            role.isDv() -> newCycleIndicator.region
            role.isGr() -> newCycleIndicator.zone
            else -> newCycleIndicator.section
        }
    }

    private fun createNewCycleIndicatorSale(listIndicator: List<NewCycleIndicator>): NewCycleGridIndicatorModel {
        if (listIndicator.size < KpiRules.MINIMUM_CAMPAIGNS) return NewCycleGridIndicatorModel()
        return map(listIndicator.first())
    }

    private fun createNewCycleIndicatorBilling(listIndicator: List<NewCycleIndicator>): NewCycleGridIndicatorModel {
        if (listIndicator.size < KpiRules.MAXIMUM_CAMPAIGNS) return NewCycleGridIndicatorModel()
        val previousIndicator = listIndicator.first()
        val currentIndicator = listIndicator.last()
        return map(currentIndicator = currentIndicator, previousIndicator = previousIndicator)
    }

    private fun map(newCycleIndicator: NewCycleIndicator): NewCycleGridIndicatorModel =
        with(newCycleIndicator) {
            return@with NewCycleGridIndicatorModel(
                campaign = campaign,
                region = region,
                zone = zone,
                section = section,
                lowValue6d6 = lowValueOrders5d5.toString(),
                lowValue5d5 = lowValueOrders4d4.toString(),
                lowValue4d4 = lowValueOrders3d3.toString(),
                lowValue3d3 = lowValueOrders2d2.toString(),
                lowValue2d2 = lowValueOrders1d1.toString(),
                highValue6d6 = highValueOrders5d5.toString(),
                highValue5d5 = highValueOrders4d4.toString()
            )
        }

    private fun map(
        currentIndicator: NewCycleIndicator,
        previousIndicator: NewCycleIndicator
    ): NewCycleGridIndicatorModel {
        return NewCycleGridIndicatorModel(
            campaign = currentIndicator.campaign,
            region = currentIndicator.region,
            zone = currentIndicator.zone,
            section = currentIndicator.section,
            lowValue6d6 = formatIndicatorSummary(
                currentIndicator.lowValueOrders6d6,
                previousIndicator.lowValueOrders5d5
            ),
            lowValue5d5 = formatIndicatorSummary(
                currentIndicator.lowValueOrders5d5,
                previousIndicator.lowValueOrders4d4
            ),
            lowValue4d4 = formatIndicatorSummary(
                currentIndicator.lowValueOrders4d4,
                previousIndicator.lowValueOrders3d3
            ),
            lowValue3d3 = formatIndicatorSummary(
                currentIndicator.lowValueOrders3d3,
                previousIndicator.lowValueOrders2d2
            ),
            lowValue2d2 = formatIndicatorSummary(
                currentIndicator.lowValueOrders2d2,
                previousIndicator.lowValueOrders1d1
            ),
            highValue6d6 = formatIndicatorSummary(
                currentIndicator.highValueOrders6d6,
                previousIndicator.highValueOrders5d5
            ),
            highValue5d5 = formatIndicatorSummary(
                currentIndicator.highValueOrders5d5,
                previousIndicator.highValueOrders4d4
            ),
            highValue4d4 = formatIndicatorSummary(
                currentIndicator.highValueOrders4d4,
                currentIndicator.highValueOrders4d4
            )
        )
    }

    private fun formatIndicatorSummary(currentVal: Int, previousVal: Int): String {
        return textResolver.formatSegmentText(currentVal.toString(), previousVal.toString())
    }
}
