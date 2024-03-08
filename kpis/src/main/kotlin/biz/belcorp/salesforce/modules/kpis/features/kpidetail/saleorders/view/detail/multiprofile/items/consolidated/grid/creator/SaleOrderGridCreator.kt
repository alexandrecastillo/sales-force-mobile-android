package biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.detail.multiprofile.items.consolidated.grid.creator

import androidx.annotation.StringRes
import biz.belcorp.mobile.components.design.spreadsheet.models.Cell
import biz.belcorp.mobile.components.design.spreadsheet.models.Column
import biz.belcorp.mobile.components.design.spreadsheet.objects.CellType
import biz.belcorp.salesforce.core.constants.Constant.EMPTY_STRING
import biz.belcorp.salesforce.core.constants.Constant.HYPHEN
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ONE
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_THREE
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_TWO
import biz.belcorp.salesforce.core.constants.Constant.NUMBER_ZERO
import biz.belcorp.salesforce.core.domain.entities.ua.UaInfo
import biz.belcorp.salesforce.core.domain.entities.zonificacion.LlaveUA
import biz.belcorp.salesforce.core.entities.zonificacion.Rol
import biz.belcorp.salesforce.core.utils.*
import biz.belcorp.salesforce.modules.kpis.R
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders.GridUaInfo
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders.SaleOrdersConsolidated
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders.SaleOrdersIndicator
import biz.belcorp.salesforce.modules.kpis.core.domain.entities.saleorders.SaleOrdersOrderRange
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.SaleOrdersTextResolver
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.model.SaleOrderGridType
import biz.belcorp.salesforce.modules.kpis.features.kpidetail.saleorders.view.detail.multiprofile.items.consolidated.grid.*
import biz.belcorp.salesforce.modules.kpis.utils.DateUtils

class SaleOrderGridCreator private constructor(private val textResolver: SaleOrdersTextResolver) {

    companion object {
        fun init(saleOrdersTextResolver: SaleOrdersTextResolver) =
            SaleOrderGridCreator(saleOrdersTextResolver)
    }

    fun getColumns(
        model: SaleOrdersConsolidated,
        @SaleOrderGridType saleOrderType: String
    ): List<Column> = with(model) {
        return when (saleOrderType) {
            SaleOrderGridType.SALES -> getSalesColumns(this)
            SaleOrderGridType.ORDERS -> getOrdersColumns(this)
            SaleOrderGridType.ACTIVITY -> getActivityColumns(this)
            SaleOrderGridType.ACTIVES_RETENTION -> getActivesRetention(this)
            else -> listOf()
        }
    }

    private fun getSalesColumns(model: SaleOrdersConsolidated): List<Column> {
        val headers = listOf(
            UaHeader(getHeaderLabel(model.role)),
            SaleHeaderType.SaleReal(getStringValue(R.string.text_real)),
            SaleHeaderType.SaleGoal(getStringValue(R.string.text_goal)),
            SaleHeaderType.SaleFulfillment(getStringValue(R.string.text_fulfillment)),
            SaleHeaderType.SaleAverage(getStringValue(R.string.text_average))
        )
        return createGridInformation(headers, model)
    }

    private fun getOrdersColumns(model: SaleOrdersConsolidated): List<Column> {
        val headers = listOf(
            UaHeader(getHeaderLabel(model.role)),
            OrderHeaderType.OrderLowValue(getStringValue(R.string.text_low_value)),
            OrderHeaderType.OrderHighValue(getStringValue(R.string.text_high_value)),
            OrderHeaderType.OrderHighPlusValue(getStringValue(R.string.text_high_value_plus)),
            OrderHeaderType.OrderReal(getStringValue(R.string.text_real)),
            OrderHeaderType.OrderGoal(getStringValue(R.string.text_goal)),
            OrderHeaderType.OrderFulfillment(getStringValue(R.string.text_fulfillment))
        )
        return createGridInformation(headers, model)
    }

    private fun getActivityColumns(model: SaleOrdersConsolidated): List<Column> {
        val headers = listOf(
            UaHeader(getHeaderLabel(model.role)),
            ActivityHeaderType.ActivityReal(getStringValue(R.string.text_real)),
            ActivityHeaderType.ActivityGoal(getStringValue(R.string.text_goal)),
            ActivityHeaderType.ActivityFulfillment(getStringValue(R.string.text_fulfillment))
        )
        return createGridInformation(headers, model)
    }

    private fun getActivesRetention(model: SaleOrdersConsolidated): List<Column> {
        val lastYear = DateUtils.getLastYear().toString()
        val campaign = model.campaign
        val headers = listOf(
            UaHeader(getHeaderLabel(model.role)),
            ActivesHeaderType.ActivesFinal(
                getStringValue(R.string.text_final_actives, campaign.nombreCorto.takeLastTwo())
            ),
            ActivesHeaderType.ActivesLastYear(
                getStringValue(R.string.text_final_actives_last_year, lastYear)
            ),
            ActivesHeaderType.ActivesRetention(getStringValue(R.string.text_actives_retention_percentage)),
            ActivesHeaderType.ActivesGoal(getStringValue(R.string.text_goal)),
            ActivesHeaderType.ActivesFulfillment(getStringValue(R.string.text_fulfillment))
        )
        return createGridInformation(headers, model)
    }

    private fun createGridInformation(
        headers: List<HeaderType>,
        model: SaleOrdersConsolidated
    ): List<Column> {
        val currencySymbol = model.configuration.currencySymbol
        val isParent = model.isParent
        return headers.map { header ->
            val cells = model.pairData.map { createCells(header, isParent, currencySymbol, it) }
            createColumn(header.title, cells)
        }
    }

    private fun createCells(
        headerType: HeaderType,
        isParent: Boolean,
        currency: String,
        model: Pair<GridUaInfo, SaleOrdersIndicator?>
    ): Cell {
        return when (headerType) {
            is SaleHeaderType -> createCellForSale(headerType, currency, model)
            is OrderHeaderType -> createCellForOrder(headerType, model)
            is ActivityHeaderType -> createCellForActivity(headerType, model)
            is ActivesHeaderType -> createCellForActives(headerType, model)
            is UaHeader -> createCellForUa(model.first.uaInfo, isParent)
        }
    }

    private fun createCellForUa(model: UaInfo, isParent: Boolean): Cell = with(model) {
        return if (!isThirdPerson && isParent) createUaCell(getParentLabel(role))
        else createUaCell(getChildrenLabel(uaKey, role))
    }

    private fun createCellForSale(
        headerType: SaleHeaderType,
        currency: String,
        model: Pair<GridUaInfo, SaleOrdersIndicator?>
    ): Cell {
        val entity = model.second ?: return createCell(HYPHEN)
        val role = model.first.uaInfo.role
        return when (headerType) {
            is SaleHeaderType.SaleReal ->
                if(role.isMultiProfile()) createCurrencyCell(currency, entity.netSale.zeroIfNull())
                else createCurrencyCell(currency, entity.catalogSale.zeroIfNull())
            is SaleHeaderType.SaleGoal ->
                if (role.isMultiProfile())
                    createCurrencyCell(currency, entity.netSaleGoal.zeroIfNull())
                else createCell(HYPHEN)
            is SaleHeaderType.SaleFulfillment ->
                if (role.isMultiProfile())
                    createPercentageCell(entity.fulfillmentNetSalePercentage.zeroIfNull())
                else createCell(HYPHEN)
            is SaleHeaderType.SaleAverage ->
                createCurrencyCell(currency, entity.averageAmount.zeroIfNull())
        }
    }

    private fun createCellForOrder(
        headerType: OrderHeaderType,
        model: Pair<GridUaInfo, SaleOrdersIndicator?>
    ): Cell {
        val isBright = model.first.iBright
        val role = model.first.uaInfo.role
        val entity = model.second ?: return createCell(HYPHEN)
        val lowValue = getRangeValue(entity.ordersRange, NUMBER_ZERO)
        val highValue = getRangeValue(entity.ordersRange, NUMBER_TWO)
        val highValuePlus = getRangeValue(entity.ordersRange, NUMBER_THREE)

        return when (headerType) {
            is OrderHeaderType.OrderLowValue -> createRangeCell(lowValue)
            is OrderHeaderType.OrderHighValue -> createRangeCell(highValue)
            is OrderHeaderType.OrderHighPlusValue -> createRangeCell(highValuePlus)
            is OrderHeaderType.OrderReal -> createCell(entity.orders.formatWithThousands())
            is OrderHeaderType.OrderGoal ->
                if (role.isMultiProfile() || !isBright) createCell(entity.ordersGoal.formatWithThousands())
                else createCell(HYPHEN)
            is OrderHeaderType.OrderFulfillment ->
                if (role.isMultiProfile() || !isBright) createPercentageCell(entity.fulfillmentOrderPercentage)
                else createCell(HYPHEN)
        }
    }

    private fun createCellForActivity(
        headerType: ActivityHeaderType,
        model: Pair<GridUaInfo, SaleOrdersIndicator?>
    ): Cell {
        val role = model.first.uaInfo.role
        val entity = model.second ?: return createCell(HYPHEN)

        return when (headerType) {
            is ActivityHeaderType.ActivityReal -> createPercentageCell(entity.activityPercentage)
            is ActivityHeaderType.ActivityGoal ->
                if (role.isMultiProfile()) createPercentageCell(entity.activityGoal)
                else createCell(HYPHEN)
            is ActivityHeaderType.ActivityFulfillment ->
                if (role.isMultiProfile()) createPercentageCell(entity.fulfillmentActivityPercentage)
                else createCell(HYPHEN)
        }
    }

    private fun createCellForActives(
        headerType: ActivesHeaderType,
        model: Pair<GridUaInfo, SaleOrdersIndicator?>
    ): Cell {
        val role = model.first.uaInfo.role
        val entity = model.second ?: return createCell(HYPHEN)

        return when (headerType) {
            is ActivesHeaderType.ActivesFinal -> createCell(entity.activesFinals.formatWithThousands())
            is ActivesHeaderType.ActivesLastYear -> createCell(entity.activesFinalsLastYear.formatWithThousands())
            is ActivesHeaderType.ActivesRetention -> createPercentageCell(entity.activesRetentionPercentage)
            is ActivesHeaderType.ActivesGoal ->
                if (role.isMultiProfile()) createPercentageCell(entity.activesRetentionGoal)
                else createCell(HYPHEN)
            is ActivesHeaderType.ActivesFulfillment ->
                if (role.isMultiProfile()) createPercentageCell(entity.fulfillmentRetentionPercentage)
                else createCell(HYPHEN)
        }
    }

    private fun createCell(value: String, type: Int = CellType.DATA): Cell {
        return Cell(value = value, type = type)
    }

    private fun createUaCell(value: String) = createCell(value, type = CellType.ROW_HEADER)

    private fun createColumn(title: String, items: List<Cell>): Column {
        val titleCell = Cell(value = title.toUpperCase(), type = CellType.HEADER_COLUMN)
        return Column(title = titleCell, values = items, isRowHeader = false)
    }

    private fun getHeaderLabel(role: Rol): String = with(role) {
        return when {
            isDV() -> textResolver.context.getString(R.string.text_country_and_regions)
            isGR() -> textResolver.context.getString(R.string.text_region_and_zones)
            isGZ() -> textResolver.context.getString(R.string.text_zone_and_sections)
            else -> EMPTY_STRING
        }
    }

    private fun getParentLabel(role: Rol): String = with(role) {
        return when {
            isDV() -> getStringValue(R.string.your_country)
            isGR() -> getStringValue(R.string.your_region)
            isGZ() -> getStringValue(R.string.your_zone)
            else -> EMPTY_STRING
        }
    }

    private fun getChildrenLabel(uaKey: LlaveUA, role: Rol): String = with(role) {
        return when {
            isGR() -> uaKey.codigoRegion.orEmpty()
            isGZ() -> uaKey.codigoZona.orEmpty()
            isSE() -> uaKey.codigoSeccion.orEmpty()
            else -> EMPTY_STRING
        }
    }

    private fun getStringValue(@StringRes resId: Int, vararg args: Any?) =
        textResolver.context.getString(resId, *args)

    private fun createCurrencyCell(currency: String, amount: Double) =
        createCell(textResolver.formatCurrencyAmount(currency, amount.formatWithLowerThousands()))

    private fun createPercentageCell(percentage: Double) =
        createCell(textResolver.formatPercentageStringLabel(percentage.toPercentageTruncateDecimal()))

    private fun getRangeValue(
        range: List<SaleOrdersOrderRange>,
        position: Int
    ): SaleOrdersOrderRange? {
        return range.firstOrNull { it.position == position }
    }

    private fun createRangeCell(item: SaleOrdersOrderRange?): Cell {
        return if (item.isNotNull()) createCell(item?.amount.zeroIfNull().formatWithThousands())
        else createCell(HYPHEN)
    }
}
